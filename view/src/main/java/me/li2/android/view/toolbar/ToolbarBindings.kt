/*
 * Created by Weiyi Li on 2019-11-03.
 * https://github.com/li2
 */
package me.li2.android.view.toolbar

import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.AppBarLayout.LayoutParams
import com.google.android.material.appbar.AppBarLayout.LayoutParams.*
import com.google.android.material.appbar.CollapsingToolbarLayout
import me.li2.android.common.logic.orFalse

object ToolbarBindings {
    @JvmStatic
    @BindingAdapter("app:collapsingScrollEnabled")
    fun setCollapsingToolbarLayoutScrollEnabled(collapsingToolbarLayout: CollapsingToolbarLayout,
                                                enabled: Boolean?) {
        val lp = collapsingToolbarLayout.layoutParams as LayoutParams
        if (enabled.orFalse()) {
            lp.scrollFlags = SCROLL_FLAG_SCROLL or SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
        } else {
            lp.scrollFlags = SCROLL_FLAG_SNAP
        }
        collapsingToolbarLayout.layoutParams = lp
    }
}