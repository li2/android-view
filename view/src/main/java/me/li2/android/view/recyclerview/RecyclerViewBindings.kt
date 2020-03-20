package me.li2.android.view.recyclerview

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.li2.android.common.number.orZero

object RecyclerViewBindings {
    @JvmStatic
    @BindingAdapter("app:linearSpacing")
    fun setItemLinearSpacing(recyclerView: RecyclerView, spacing: Float?) {
        val orientation = (recyclerView.layoutManager as? LinearLayoutManager)?.orientation
                ?: RecyclerView.VERTICAL
        recyclerView.addItemDecoration(LinearSpacingDecoration(orientation, spacing?.toInt().orZero()))
    }
}
