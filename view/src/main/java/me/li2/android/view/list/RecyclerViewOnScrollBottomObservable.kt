package me.li2.android.view.list

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Emitter
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable
import me.li2.android.common.number.orZero

/**
 * An observable which emits value when RecyclerView was scrolled to bottom.
 * then throttleFirst can be used to avoid duplicated API calls.
 */
fun RecyclerView.onScrolledBottom(): Observable<Unit> {
    return Observable.create(RecyclerViewOnScrollBottomObservable(this))
}

private class RecyclerViewOnScrollBottomObservable(private val recyclerView: RecyclerView)
    : ObservableOnSubscribe<Unit>, Disposable {

    private lateinit var emitter: Emitter<Unit>
    private var onScrollListener: RecyclerView.OnScrollListener?

    init {
        onScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = recyclerView.layoutManager?.itemCount.orZero()
                val visibleItemCount = recyclerView.layoutManager?.childCount.orZero()
                val firstVisibleItemPosition = (recyclerView.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition().orZero()
                if (firstVisibleItemPosition == 0 && totalItemCount == 1) {
                    // ignore this case
                    return
                }
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
                    emitter.onNext(Unit)
                }
            }
        }
    }

    override fun subscribe(emitter: ObservableEmitter<Unit>) {
        this.emitter = emitter
        onScrollListener?.let {
            recyclerView.addOnScrollListener(it)
        }
    }

    override fun dispose() {
        onScrollListener?.let {
            recyclerView.removeOnScrollListener(it)
        }
    }

    override fun isDisposed() = onScrollListener != null
}
