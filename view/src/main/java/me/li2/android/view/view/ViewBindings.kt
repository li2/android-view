package me.li2.android.view.view

import android.view.View
import android.view.View.*
import androidx.databinding.BindingAdapter
import me.li2.android.common.logic.orFalse

object ViewBindings {
    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setViewVisibility(view: View, value: Boolean?) {
        view.visibility = if (value.orFalse()) VISIBLE else GONE
    }

    @JvmStatic
    @BindingAdapter("app:invisibility")
    fun setViewInvisibility(view: View, value: Boolean?) {
        view.visibility = if (value.orFalse()) INVISIBLE else VISIBLE
    }
}
