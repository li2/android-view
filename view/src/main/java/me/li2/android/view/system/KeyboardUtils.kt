/*
 * Created by weiyi on 2019-10-28.
 * https://github.com/li2
 */
@file:JvmName("KeyboardUtils")
package me.li2.android.view.system

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Activity.hideKeyboard() {
    currentFocus?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Fragment.hideKeyboard() = activity?.hideKeyboard()
