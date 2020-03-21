package me.li2.android.view.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import me.li2.android.view.R
import me.li2.android.view.toast.toast

/**
 * A transparent overlay view shows on top of the screen to block user touch events,
 * and toasts "Loading…" when user touches on screen.
 * - This view is useful for waiting Api, just simply update its visibility base on loading status.
 * - You might want to change the toast message by app:toastMessage="@{}"
 */
class LoadingOverlayView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr), View.OnClickListener {

    var toastMessage: String

    init {
        inflate(context, R.layout.loading_overlay_view, null)
        // with hard-coding elevation to make sure it CAN block all the touch events
        ViewCompat.setElevation(this, resources.getDimensionPixelSize(R.dimen.viewLib_loading_overlay_elevation) * 1.0f)
        setOnClickListener(this)
        toastMessage = context.resources.getString(R.string.viewlib_loadingoverlay_loading)
    }

    override fun onClick(v: View?) {
        context.toast(toastMessage)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("app:toastMessage")
        fun setUserAgentString(loadingOverlayView: LoadingOverlayView, toastMessage: String?) {
            toastMessage?.let { loadingOverlayView.toastMessage = it }
        }
    }
}
