/*
 * Created by weiyi on 2019-10-28.
 * https://github.com/li2
 */
package me.li2.android.view.textview

import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import androidx.databinding.BindingAdapter
import me.li2.android.common.logic.orFalse

object EditTextViewBindings {
    /**
     * @see <a href="https://stackoverflow.com/questions/3406534/password-hint-font-in-android/3444882#3444882">fix hint font mess issue when set EditText inputType as textPassword.</a>
     */
    @JvmStatic
    @BindingAdapter("app:passwordInputType")
    fun setPasswordInputType(editText: EditText, enabled: Boolean?) {
        if (enabled.orFalse()) {
            editText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            editText.transformationMethod = PasswordTransformationMethod()
        }
    }
}
