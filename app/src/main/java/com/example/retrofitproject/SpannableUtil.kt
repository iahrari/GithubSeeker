package com.example.retrofitproject

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.View
import android.widget.Toast

class SpannableUtil {
    companion object{
        @JvmStatic
        fun makeSpannableText(description: String?, text: String?): SpannableString? =
            if (description != null && text != null)
            SpannableString(description + text).apply {
                setSpan(URLSpan(text), description.length, description.length + text.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            else
                null


        @JvmStatic
        fun makeClickableText(description: String?, text: String?): SpannableString? =
            if (description != null && text != null)
                SpannableString(description + text).apply {
                    setSpan(object: ClickableSpan(){
                        override fun onClick(widget: View) {
                            (widget.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).primaryClip = ClipData.newPlainText(description, text)
                            Toast.makeText(widget.context, "Text copied to clipboard.", Toast.LENGTH_LONG).show()
                        }
                    }, description.length, description.length + text.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            else
                null

    }
}