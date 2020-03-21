package me.li2.android.view.button

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.util.Property
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator

// https://github.com/koushikcse/LoadingButton

internal class CircularAnimatedDrawable(color: Int, private val mBorderWidth: Float)
    : Drawable(), Animatable {

    private val fBounds = RectF()

    private var mObjectAnimatorSweep: ObjectAnimator? = null
    private var mObjectAnimatorAngle: ObjectAnimator? = null
    private var mModeAppearing: Boolean = false
    private val mPaint: Paint = Paint()
    private var mCurrentGlobalAngleOffset: Float = 0f
    var currentGlobalAngle: Float = 0f
        set(currentGlobalAngle) {
            field = currentGlobalAngle
            invalidateSelf()
        }
    var currentSweepAngle: Float = 0f
        set(currentSweepAngle) {
            field = currentSweepAngle
            invalidateSelf()
        }
    private var mRunning: Boolean = false

    private val mAngleProperty = object : Property<CircularAnimatedDrawable, Float>(Float::class.java, "angle") {
        override fun get(drawable: CircularAnimatedDrawable): Float {
            return drawable.currentGlobalAngle
        }

        override fun set(drawable: CircularAnimatedDrawable, value: Float?) {
            drawable.currentGlobalAngle = value ?: 0f
        }
    }

    private val mSweepProperty = object : Property<CircularAnimatedDrawable, Float>(Float::class.java, "arc") {
        override fun get(drawable: CircularAnimatedDrawable): Float {
            return drawable.currentSweepAngle
        }

        override fun set(drawable: CircularAnimatedDrawable, value: Float?) {
            drawable.currentSweepAngle = value ?: 0f
        }
    }

    init {
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = mBorderWidth
        mPaint.color = color

        setupAnimations()
    }

    override fun draw(canvas: Canvas) {
        var startAngle = currentGlobalAngle - mCurrentGlobalAngleOffset
        var sweepAngle = currentSweepAngle
        if (!mModeAppearing) {
            startAngle += sweepAngle
            sweepAngle = 360f - sweepAngle - MIN_SWEEP_ANGLE.toFloat()
        } else {
            sweepAngle += MIN_SWEEP_ANGLE.toFloat()
        }
        canvas.drawArc(fBounds, startAngle, sweepAngle, false, mPaint)
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        mPaint.colorFilter = cf
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSPARENT
    }

    private fun toggleAppearingMode() {
        mModeAppearing = !mModeAppearing
        if (mModeAppearing) {
            mCurrentGlobalAngleOffset = (mCurrentGlobalAngleOffset + MIN_SWEEP_ANGLE * 2) % 360
        }
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        fBounds.left = bounds.left.toFloat() + mBorderWidth / 2f + .5f
        fBounds.right = bounds.right.toFloat() - mBorderWidth / 2f - .5f
        fBounds.top = bounds.top.toFloat() + mBorderWidth / 2f + .5f
        fBounds.bottom = bounds.bottom.toFloat() - mBorderWidth / 2f - .5f
    }

    private fun setupAnimations() {
        mObjectAnimatorAngle = ObjectAnimator.ofFloat(this, mAngleProperty, 360f).apply {
            interpolator = ANGLE_INTERPOLATOR
            duration = ANGLE_ANIMATOR_DURATION.toLong()
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
        }

        mObjectAnimatorSweep = ObjectAnimator.ofFloat(this, mSweepProperty, 360f - MIN_SWEEP_ANGLE * 2).apply {
            interpolator = SWEEP_INTERPOLATOR
            duration = SWEEP_ANIMATOR_DURATION.toLong()
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {}

                override fun onAnimationCancel(animation: Animator) {}

                override fun onAnimationRepeat(animation: Animator) {
                    toggleAppearingMode()
                }
            })
        }
    }

    override fun start() {
        if (isRunning) {
            return
        }
        mRunning = true
        mObjectAnimatorAngle?.start()
        mObjectAnimatorSweep?.start()
        invalidateSelf()
    }

    override fun stop() {
        if (!isRunning) {
            return
        }
        mRunning = false
        mObjectAnimatorAngle?.cancel()
        mObjectAnimatorSweep?.cancel()
        invalidateSelf()
    }

    override fun isRunning(): Boolean {
        return mRunning
    }

    companion object {
        private val ANGLE_INTERPOLATOR = LinearInterpolator()
        private val SWEEP_INTERPOLATOR = DecelerateInterpolator()
        private const val ANGLE_ANIMATOR_DURATION = 1000
        private const val SWEEP_ANIMATOR_DURATION = 1000
        const val MIN_SWEEP_ANGLE = 30
    }

}
