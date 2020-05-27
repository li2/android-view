/*
 * Created by weiyi on 2019-10-28.
 * https://github.com/li2
 */
@file:JvmName("StatusBarUtils")
@file:Suppress("unused")
package me.li2.android.view.system

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import me.li2.android.common.framework.doFromSdk

fun Activity.showStatusBar() {
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

@SuppressLint("NewApi")
fun Activity.hideStatusBar() {
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    doFromSdk(Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = Color.TRANSPARENT
    }
}
