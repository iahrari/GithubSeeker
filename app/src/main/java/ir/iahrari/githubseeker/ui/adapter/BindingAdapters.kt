package ir.iahrari.githubseeker.ui.adapter

import android.text.method.LinkMovementMethod
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("setMovement")
    fun setMovementMethod(view: TextView, isEnabled: Boolean) {
        view.movementMethod = if (isEnabled) LinkMovementMethod.getInstance() else null
    }

    @JvmStatic
    @BindingAdapter("app:setSelected")
    fun setSelected(view: TextView, isEnabled: Boolean){
        view.isSelected = isEnabled
    }
}