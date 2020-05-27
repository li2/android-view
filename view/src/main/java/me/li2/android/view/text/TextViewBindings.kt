/*
 * Created by weiyi on 2019-10-28.
 * https://github.com/li2
 */
@file:Suppress("unused")
package me.li2.android.view.text

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.TextView
import androidx.databinding.BindingAdapter

object TextViewBindings {

    @Suppress("DEPRECATION")
    @JvmStatic
    @BindingAdapter("htmlText")
    fun setHtmlText(textView: TextView, stringRes: String) {
        fun fromHtmlCompat(source: String): Spanned {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(source)
            }
        }
        textView.text = fromHtmlCompat(stringRes)
    }
}
