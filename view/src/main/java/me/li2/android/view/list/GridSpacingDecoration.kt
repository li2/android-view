@file:Suppress("unused")
package me.li2.android.view.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @see <a href="https://stackoverflow.com/a/30701422/2722270">Android Recyclerview GridLayoutManager column spacing</a>
 */
class GridSpacingDecoration(private val spanCount: Int,
                            private val spacing: Int,
                            private val includeEdge: Boolean = false)
    : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect,
                                view: View,
                                parent: RecyclerView,
                                state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column
        val left: Int// spacing - column * ((1f / spanCount) * spacing)
        val right: Int // (column + 1) * ((1f / spanCount) * spacing)
        var top = 0
        var bottom = 0  // item bottom
        if (includeEdge) {
            left = spacing - column * spacing / spanCount
            right = (column + 1) * spacing / spanCount
            if (position < spanCount) { // top edge
                top = spacing
            }
            bottom = spacing
            outRect.set(left, top, right, bottom)
        } else {
            left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
            right = spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                top = spacing // item top
            }
            outRect.set(left, top, right, bottom)
        }
    }
}
