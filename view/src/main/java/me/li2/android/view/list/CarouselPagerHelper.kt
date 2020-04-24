/*
 * Created by weiyi on 2020-04-18.
 * https://github.com/li2
 */
@file:Suppress("unused")
package me.li2.android.view.list

import androidx.recyclerview.widget.ListAdapter

/**
 * Provide carousel scroll ability for RecyclerView [ListAdapter] by mocking up
 * the limited dataset with [Int.MAX_VALUE].
 *
 * -         class YourListAdapter : ListAdapter<Item, ItemViewHolder>(DIFF_CALLBACK), CarouselPagerHelper {
 * -             override val carouselDatasetSize: Int
 * -                 get() = currentList.size
 * -             override fun onBindViewHolder(viewHolder: TopItemViewHolder, position: Int) {
 * -                 val dataPosition = getCarouselDataPosition(position)
 * -                 viewHolder.bind(getItem(dataPosition))
 * -             }
 * -             override fun getItemCount() = getCarouselDisplaySize()
 * -         }
 */
interface CarouselPagerHelper {

    /**
     * Override by ListAdapter to provide the real data set size.
     */
    val carouselDatasetSize: Int

    /**
     * Return the position in data set to allow ListAdapter.onBindViewHolder
     * to bind correct data.
     */
    fun getCarouselDataPosition(displayPosition: Int) =
            if (carouselDatasetSize > 0) displayPosition % carouselDatasetSize else 0

    /**
     * Override ListAdapter.getItemCount() to provide a count which can
     * simulate infinite loop effect.
     *
     * Usage: override fun getItemCount() = getCarouselPagesCount()
     */
    fun getCarouselDisplaySize() =
            if (carouselDatasetSize > 0) Int.MAX_VALUE else 0

    /**
     * Return initial position to allow ViewPager to swipe to left.
     *
     * Usage: viewPager.setCurrentItem(adapter.getCarouselInitialPosition(), false)
     */
    fun getCarouselInitialDisplayPosition() =
            if (carouselDatasetSize > 0) 10000 / carouselDatasetSize * carouselDatasetSize else 0
}