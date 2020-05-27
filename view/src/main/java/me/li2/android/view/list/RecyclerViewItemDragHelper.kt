/*
 * Created by weiyi on 2019-10-28.
 * https://github.com/li2
 */
@file:Suppress("unused")
package me.li2.android.view.list

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_DRAG
import androidx.recyclerview.widget.RecyclerView

interface OnRecyclerItemDragListener {
    fun onItemMoved(fromPosition: Int, toPosition: Int)
    fun onItemSwiped(position: Int)
}

/**
 * ItemTouchHelper to help drag and swipe items in RecyclerView.
 * You don't need to call [ItemTouchHelper.attachToRecyclerView]
 *
 * @param listener callback when item was moved or swiped.
 * @param isLongPressDragEnabled true to allow drag and drop operation when an item is long pressed.
 *  you may want to disable this if you want to start dragging on a custom view touch using [ItemTouchHelper.startDrag].
 * @param isItemViewSwipeEnabled true to allow a swipe operation if a pointer is swiped over the View.
 *  you may want to disable this if you want to start swiping on a custom view touch using [ItemTouchHelper.startSwipe].
 * @return [ItemTouchHelper]
 */
fun RecyclerView.itemDragHelper(listener: OnRecyclerItemDragListener,
                                isLongPressDragEnabled: Boolean = true,
                                isItemViewSwipeEnabled: Boolean = true): ItemTouchHelper {
    return ItemTouchHelper(RecyclerViewItemDragHelper(listener, isLongPressDragEnabled, isItemViewSwipeEnabled)).also {
        it.attachToRecyclerView(this)
    }
}

private class RecyclerViewItemDragHelper(
        private val listener: OnRecyclerItemDragListener,
        private val isLongPressDragEnabled: Boolean,
        private val isItemViewSwipeEnabled: Boolean) : ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled(): Boolean = isLongPressDragEnabled

    override fun isItemViewSwipeEnabled(): Boolean = isItemViewSwipeEnabled

    override fun getMovementFlags(recyclerView: RecyclerView,
                                  viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        // Scale the row while being selected
        if (actionState == ACTION_STATE_DRAG) {
            scaleViewHolder(viewHolder, true)
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        scaleViewHolder(viewHolder, false)
    }

    override fun onMove(recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder): Boolean {
        listener.onItemMoved(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onItemSwiped(viewHolder.adapterPosition)
    }

    private fun scaleViewHolder(viewHolder: RecyclerView.ViewHolder?, enabled: Boolean) {
        viewHolder?.itemView?.let { itemView ->
            itemView.scaleX = if (enabled) 1.05f else 1f
            itemView.scaleY = if (enabled) 1.05f else 1f
        }
    }
}
