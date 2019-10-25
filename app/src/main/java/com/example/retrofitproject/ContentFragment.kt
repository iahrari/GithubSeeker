package com.example.retrofitproject

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.retrofitproject.databinding.FragmentContentBinding
import com.pddstudio.highlightjs.HighlightJsView
import com.pddstudio.highlightjs.models.Language
import com.pddstudio.highlightjs.models.Theme
import kotlinx.coroutines.*
import java.nio.charset.StandardCharsets

class ContentFragment : Fragment() {
    //TODO: Add ViewModel for this fragment

    private lateinit var type: ContentType
    private val job = Job()
    private lateinit var content: Content
    private val scope = CoroutineScope(Dispatchers.Main + job)
    private var language: Language? = null
    private lateinit var binding: FragmentContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args by navArgs<ContentFragmentArgs>()

        content = args.content
        type = getContentDataType(content.name)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_content, container, false)
        binding.type = type
        activity?.findViewById<TextView>(R.id.header_title)!!.text = content.name
        if (type == ContentType.Image) {
            setImageView(binding.image)
        } else {
            when (type) {
                ContentType.Code -> setHighlightJs(binding.highlight)
                ContentType.Markdown -> setMarkdownWebView(binding.highlight)
                else ->
                    Toast.makeText(
                        context,
                        "Opening this file is not supported by this app.",
                        Toast.LENGTH_LONG
                    ).show()
            }
        }

        return binding.root
    }

    private fun setMarkdownWebView(webView: WebView){
        scope.launch {
            try {
                val response = client.getContentMarkUpView(context!!.getToken()!!, content.url!!)
                if(response.isSuccessful)
                    webView.loadDataWithBaseURL(null, response.body()!!.string(), "text/html; charset=utf-8", "UTF-8", null)
                else
                    context?.processResponseCode(response.code())

            } catch (t: Throwable){
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setHighlightJs(highlight: HighlightJsView){
        language = findLanguageFromName(content.name)
        highlight.apply {

            theme = Theme.ZENBURN
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                isNestedScrollingEnabled = false
            setZoomSupportEnabled(true)
            setShowLineNumbers(true)
            highlightLanguage = language
            setBackgroundColor(Color.TRANSPARENT)
        }
        getContent(content.url!!, highlight)
    }

    private fun setImageView(image: ImageView){
        val url = GlideUrl(
            content.downloadURl,
            LazyHeaders.Builder().addHeader("Authorization", context!!.getToken()!!).build()
        )

        Glide.with(this).load(url).into(image)
    }

    private fun getContent(path: String, view: HighlightJsView) {
        scope.launch {
            try {
                val response = client.getContentJson(context!!.getToken()!!, path)
                if (response.isSuccessful) {
                    val decode = Base64.decode(response.body()?.content, Base64.DEFAULT)
                    view.setSource(String(decode, StandardCharsets.UTF_8))
                } else
                    context!!.processResponseCode(response.code())
            } catch (t: Throwable) {
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
}