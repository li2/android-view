/*
 * Created by weiyi on 2019-10-28.
 * https://github.com/li2
 */
@file:Suppress("unused")
package me.li2.android.view.text

import android.os.Build
import android.text.Html
import android.text.InputType
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import me.li2.android.common.logic.orFalse

object TextViewBindings {
    // https://stackoverflow.com/questions/2734270/how-to-make-links-in-a-textview-clickable
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
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    /**
     * @see <a href="https://stackoverflow.com/questions/3406534/password-hint-font-in-android/3444882#3444882">fix hint font mess issue when set EditText inputType as textPassword.</a>
     */
    @JvmStatic
    @BindingAdapter("passwordInputType")
    fun setPasswordInputType(editText: EditText, enabled: Boolean?) {
        if (enabled.orFalse()) {
            editText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            editText.transformationMethod = PasswordTransformationMethod()
        }
    }
}
