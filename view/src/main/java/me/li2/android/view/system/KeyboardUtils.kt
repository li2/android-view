/*
 * Created by weiyi on 2019-10-28.
 * https://github.com/li2
 */
@file:JvmName("KeyboardUtils")
@file:Suppress("unused")
package me.li2.android.view.system

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Activity.hideKeyboard() {
    currentFocus?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Fragment.hideKeyboard() = activity?.hideKeyboard()

/**
* Receiver: root view of the fragment or activity.
*/
fun View.observeKeyboardVisibleChanges(onVisibleChanges: (Boolean) -> Unit) {
    this.viewTreeObserver.addOnGlobalLayoutListener {
        val r = Rect()
        this.getWindowVisibleDisplayFrame(r)
        val screenHeight = this.rootView.height
        val keyboardHeight = screenHeight - r.bottom
        val visible = keyboardHeight > 0.2 * screenHeight
        onVisibleChanges(visible)
    }
}
