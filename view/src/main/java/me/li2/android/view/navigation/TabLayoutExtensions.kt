/*
 * Created by Weiyi Li on 2019-11-03.
 * https://github.com/li2
 */
@file:JvmName("TabLayoutExtensions")
package me.li2.android.view.navigation

import com.google.android.material.tabs.TabLayout

fun TabLayout.forEachIndexed(action: (index: Int, tab: TabLayout.Tab) -> Unit) {
    for (index in 0 until tabCount) {
        getTabAt(index)?.let { tab ->
            action(index, tab)
        }
    }
}
