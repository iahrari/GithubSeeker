package com.example.retrofitproject

import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.pddstudio.highlightjs.HighlightJsView
import com.pddstudio.highlightjs.models.Language
import com.pddstudio.highlightjs.models.Theme
import kotlinx.coroutines.*
import java.nio.charset.StandardCharsets

class ContentFragment : Fragment() {

    enum class ContentType{
        Markdown, Image, Code
    }

    private lateinit var type: ContentType
    private val job = Job()
    private lateinit var content: Content
    private val scope = CoroutineScope(Dispatchers.Main + job)
    private lateinit var webView: WebView
    private lateinit var highlight: HighlightJsView
    private var language: Language? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args by navArgs<ContentFragmentArgs>()
        content = args.content

        when{
            content.name.endsWith("", 1) -> type = ContentType.Markdown

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        webView = WebView(context)
        highlight = HighlightJsView(context)

        language = findLanguageFromName(content.name)
        highlight.apply {
            theme = Theme.ANDROID_STUDIO
            setZoomSupportEnabled(true)
            setShowLineNumbers(true)
            setOnLanguageChangedListener {
                Toast.makeText(context, it.getName(), Toast.LENGTH_LONG).show()
            }
            highlightLanguage = language
        }

        activity?.findViewById<TextView>(R.id.header_title)!!.text = content.name
        getContentHtml(content.url!!)
        return inflater.inflate(R.layout.fragment_content, container, false)
    }

    private fun getContentHtml(path: String) {
        scope.launch {
            try {
                val response = client.getContentJson(context!!.getToken()!!, path)
                if (response.isSuccessful) {
                    val decode = Base64.decode(response.body()?.content, Base64.DEFAULT)
                    highlight.setSource(String(decode, StandardCharsets.UTF_8))
                } else
                    context!!.processResponseCode(response.code())
            } catch (t: Throwable) {
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
}