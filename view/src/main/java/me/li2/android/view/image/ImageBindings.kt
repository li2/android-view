/*
 * Created by weiyi on 2019-10-28.
 * https://github.com/li2
 */
package me.li2.android.view.image

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import me.li2.android.common.logic.orFalse

object ImageBindings {
    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageResource(imageView: ImageView, resource: Int) {
        imageView.setImageResource(resource)
    }

    /**
     * @param src can be url, data uri (such as base 64)
     * @param fallbackImageUrl show this image if error happens.
     */
    @JvmStatic
    @BindingAdapter(value = [
        "android:src",
        "fallbackImageUrl",
        "placeHolder",
        "errorPlaceholder",
        "centerCrop",
        "circleCrop",
        "fitCenter",
        "onComplete"
    ], requireAll = false)
    fun setImageUrl(view: ImageView,
                    src: String?,
                    fallbackImageUrl: String?,
                    placeHolder: Drawable?,
                    errorPlaceholder: Drawable?,
                    centerCrop: Boolean?,
                    circleCrop: Boolean?,
                    fitCenter: Boolean?,
                    glideRequestListener: GlideRequestListener?) {
        val requestOptions = RequestOptions().apply {
            if (centerCrop.orFalse()) centerCrop()
            if (circleCrop.orFalse()) circleCrop()
            if (fitCenter.orFalse()) fitCenter()
            if (placeHolder != null) placeholder(placeHolder)
            if (errorPlaceholder != null) error(errorPlaceholder)
        }
        val requestListener = object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                glideRequestListener?.onGlideRequestComplete(false)
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                glideRequestListener?.onGlideRequestComplete(true)
                return false
            }
        }
        Glide.with(view.context)
                .load(src)
                .also {
                    if (fallbackImageUrl != null) {
                        it.error(Glide.with(view.context).load(fallbackImageUrl))
                    }
                }
                .listener(requestListener)
                .apply(requestOptions)
                .into(view)
    }
}

interface GlideRequestListener {
    fun onGlideRequestComplete(success: Boolean)
}