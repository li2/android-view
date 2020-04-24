package me.li2.android.view.list

import androidx.viewpager2.widget.ViewPager2
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import me.li2.android.common.number.orZero
import java.util.concurrent.TimeUnit

/**
 * an interface serve as a plugin to provide the ViewPager auto scroll ability, like carousel effect.
 */
interface ViewPager2AutoScrollHelper {
    /** the ViewPager instance */
    val autoScrollViewPager: ViewPager2

    /** Auto-scroll period, e.g. Pair(5L, TimeUnit.SECONDS) */
    val viewPagerAutoScrollPeriod: Pair<Long, TimeUnit>

    /** Initialize as null */
    var viewPagerAutoScrollTask: Disposable?

    private fun createAutoScrollTask(): Disposable {
        val shouldViewPagerAutoScroll = BehaviorSubject.createDefault(true)

        autoScrollViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                shouldViewPagerAutoScroll.onNext(state != ViewPager2.SCROLL_STATE_DRAGGING)
            }
        })

        return shouldViewPagerAutoScroll
                .switchMap {
                    if (it) {
                        return@switchMap Observable.interval(viewPagerAutoScrollPeriod.first, viewPagerAutoScrollPeriod.first, viewPagerAutoScrollPeriod.second)
                    } else {
                        return@switchMap Observable.empty<Long>()
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val itemCount = autoScrollViewPager.adapter?.itemCount.orZero()
                    if (itemCount > 0) {
                        val currentItem = autoScrollViewPager.currentItem
                        val isLastItem = currentItem == itemCount - 1
                        autoScrollViewPager.currentItem = if (!isLastItem) currentItem + 1 else 0
                    }
                }
    }

    /**
     * call when ViewPager data is ready to start auto scroll task.
     * Note that the auto scroll task will be paused when the user starts dragging pages.
     */
    fun startViewPagerAutoScrollTask() {
        viewPagerAutoScrollTask = createAutoScrollTask()
    }

    /**
     * call when ViewPager was destroyed. normally on Fragment.onDestroyView()
     */
    fun stopViewPagerAutoScrollTask() {
        viewPagerAutoScrollTask?.let { task ->
            if (!task.isDisposed) {
                task.dispose()
            }
        }
    }
}