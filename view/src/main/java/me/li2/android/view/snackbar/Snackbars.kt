@file:JvmName("Snackbars")
package me.li2.android.view.snackbar

import android.app.Activity
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar.Duration
import com.google.android.material.snackbar.Snackbar

fun Activity.snackbar(message: String) = snackbar(message, Snackbar.LENGTH_SHORT)
fun Activity.snackbar(@StringRes messageId: Int) = snackbar(messageId, Snackbar.LENGTH_SHORT)
fun Activity.longSnackbar(message: String) = snackbar(message, Snackbar.LENGTH_LONG)
fun Activity.longSnackbar(@StringRes messageId: Int) = snackbar(messageId, Snackbar.LENGTH_LONG)
fun Activity.indefiniteSnackbar(message: String) = snackbar(message, Snackbar.LENGTH_INDEFINITE)
fun Activity.indefiniteSnackbar(@StringRes messageId: Int) = snackbar(messageId, Snackbar.LENGTH_INDEFINITE)

fun Fragment.snackbar(message: String) = activity?.snackbar(message)
fun Fragment.snackbar(@StringRes messageId: Int) = activity?.snackbar(messageId)
fun Fragment.longSnackbar(message: String) = activity?.longSnackbar(message)
fun Fragment.longSnackbar(@StringRes messageId: Int) = activity?.longSnackbar(messageId)
fun Fragment.indefiniteSnackbar(message: String) = activity?.indefiniteSnackbar(message)
fun Fragment.indefiniteSnackbar(@StringRes messageId: Int) = activity?.indefiniteSnackbar(messageId)

private fun Activity.snackbar(@StringRes messageId: Int, @Duration duration: Int) =
        snackbar(getString(messageId), duration)

private fun Activity.snackbar(message: CharSequence, @Duration duration: Int): Snackbar = Snackbar
        .make(findViewById<View>(android.R.id.content), message, duration)
        .apply {
            show()
        }
