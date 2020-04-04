package me.li2.android.view.button

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import me.li2.android.common.logic.orFalse
import kotlin.math.min

/**
 * A material button with loading spinner, just hides text when loading. It extends from [MaterialButton],
 * which means you can use all its attrs in the xml.
 *
 * - To set loading status, just simply:
 *     - `app:isLoading="@{true}"` in xml, or
 *     - `button.isLoading = true` in code
 *
 * - TODO KNOWN ISSUE: the spinner won't be centralized if set layout_width="wrap_content"
 *
 * @see <a href="https://medium.com/@koushikcse2015/android-loading-button-show-loading-on-a-button-7209c44e19e0">Android Loading Button - Show loading on a button</a>
 */
class MaterialLoadingButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : MaterialButton(context, attrs, defStyleAttr) {

    private var animatedDrawable: CircularAnimatedDrawable? = null
    private var originalText = ""

    var isLoading: Boolean = false
        set(value) {
            field = value
            isClickable = !value
            invalidate()
        }

    init {
        originalText = text.toString()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isLoading) {
            drawIndeterminateProgress(canvas)
            text = ""
        } else {
            if (originalText.isNotEmpty()) {
                text = originalText
            }
        }
    }

    private fun drawIndeterminateProgress(canvas: Canvas) {
        if (animatedDrawable == null) {
            animatedDrawable = CircularAnimatedDrawable(currentTextColor, SPINNER_STROKE_WIDTH).apply {
                val x = (width * 0.5f).toInt()
                val y = (height * 0.5f).toInt()
                val radius = (min(width, height) * SPINNER_RADIUS_PERCENT).toInt()
                setBounds(x - radius, y - radius, x + radius, y + radius)
                callback = this@MaterialLoadingButton
                start()
            }
        } else {
            animatedDrawable?.draw(canvas)
        }
    }

    companion object {
        private const val SPINNER_RADIUS_PERCENT = 0.35f
        private const val SPINNER_STROKE_WIDTH = 8f

        @JvmStatic
        @BindingAdapter("isLoading")
        fun setLoading(button: MaterialLoadingButton, isLoading: Boolean?) {
            button.isLoading = isLoading.orFalse()
        }
    }
}
