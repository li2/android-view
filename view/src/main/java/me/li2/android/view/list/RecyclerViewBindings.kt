/*
 * Created by weiyi on 2019-10-28.
 * https://github.com/li2
 */
@file:Suppress("unused")
package me.li2.android.view.list

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.li2.android.common.number.orZero

object RecyclerViewBindings {
    @JvmStatic
    @BindingAdapter("linearSpacing")
    fun setItemLinearSpacing(recyclerView: RecyclerView, spacing: Float?) {
        val orientation = (recyclerView.layoutManager as? LinearLayoutManager)?.orientation
                ?: RecyclerView.VERTICAL
        recyclerView.addItemDecoration(LinearSpacingDecoration(orientation, spacing?.toInt().orZero()))
    }
}
