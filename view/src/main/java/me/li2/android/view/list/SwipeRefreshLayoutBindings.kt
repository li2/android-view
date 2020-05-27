@file:Suppress("unused")
package me.li2.android.view.list

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

object SwipeRefreshLayoutBindings {
    @JvmStatic
    @BindingAdapter("isRefreshing")
    fun setSwipeRefreshLayoutRefreshing(srl: SwipeRefreshLayout, value: Boolean) {
        srl.isRefreshing = value
    }
}
