package me.li2.android.view.swiperefreshlayout

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

object SwipeRefreshLayoutBindings {
    @JvmStatic
    @BindingAdapter("app:isRefreshing")
    fun setSwipeRefreshLayoutRefreshing(srl: SwipeRefreshLayout, value: Boolean) {
        srl.isRefreshing = value
    }
}
