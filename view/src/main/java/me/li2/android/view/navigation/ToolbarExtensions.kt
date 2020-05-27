/*
 * Created by weiyi on 2019-10-28.
 * https://github.com/li2
 */
@file:JvmName("ToolbarExtensions")
@file:Suppress("unused")
package me.li2.android.view.navigation

import android.app.Activity
import android.graphics.PorterDuff
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

fun Activity.setToolbar(toolbar: Toolbar, title: String = "", @DrawableRes iconId: Int? = null) {
    (this as? AppCompatActivity)?.apply {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setTitle(title)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeButtonEnabled(true)
        }
        iconId?.run { toolbar.setNavigationIcon(this) }
    }
}

fun AppBarLayout.setCollapsingListener(onCollapsed: (Boolean) -> Unit) {
    addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
        onCollapsed(abs(verticalOffset) - appBarLayout.totalScrollRange == 0)
    })
}

fun Toolbar.setIconColor(@ColorInt color: Int) {
    setBackIconColor(color)
    setMenuIconColor(color)
}

fun Toolbar.setMenuIconColor(@ColorInt color: Int) {
    for (i in 0 until menu.size()) {
        val icon = menu.getItem(i).icon
        icon?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }
}

fun Toolbar.setBackIconColor(@ColorInt color: Int) {
    navigationIcon?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
}
