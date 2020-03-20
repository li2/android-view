/*
 * Created by weiyi on 2019-10-28.
 * https://github.com/li2
 */
package me.li2.android.view.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import me.li2.android.common.number.orZero

class LinearSpacingDecoration(@Orientation val orientation: Int, private val spacing: Int)
    : ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val count = parent.adapter?.itemCount.orZero()
        // hide decoration for last item
        if (position != count - 1) {
            when (orientation) {
                VERTICAL -> outRect.bottom = spacing
                HORIZONTAL -> outRect.right = spacing
            }
        }
    }
}
