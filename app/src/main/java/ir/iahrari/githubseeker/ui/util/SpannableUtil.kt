package ir.iahrari.githubseeker.ui.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.View
import android.widget.Toast

class SpannableUtil {
    companion object{
        @JvmStatic
        fun makeSpannableText(description: String?, text: String?): SpannableString? {
            var des = description ?: ""
            return if (text != null)
            SpannableString(des + text).apply {
                setSpan(URLSpan(text), des.length, des.length + text.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            else
                null
        }

        @JvmStatic
        fun makeClickableURL(text: String?, url: String?) : SpannableString =
            SpannableString(text).apply {
                setSpan(object: ClickableSpan(){
                    override fun onClick(widget: View) {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        widget.context.startActivity(intent)
                    }
                }, 0, text!!.length , SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

        @JvmStatic
        fun makeClickableText(description: String?, text: String?): SpannableString? =
            if (description != null && text != null)
                SpannableString(description + text).apply {
                    setSpan(object: ClickableSpan(){
                        override fun onClick(widget: View) {
                            (widget.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(ClipData.newPlainText(description, text))
                            Toast.makeText(widget.context, "Text copied to clipboard.", Toast.LENGTH_LONG).show()
                        }
                    }, description.length, description.length + text.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            else
                null

    }
}