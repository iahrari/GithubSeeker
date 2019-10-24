package com.example.retrofitproject

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("setMovement")
    fun setMovementMethod(view: TextView, isEnabled: Boolean) {
        view.movementMethod = if (isEnabled) LinkMovementMethod.getInstance() else null
    }
}