/*
 * Created by weiyi on 2019-10-28.
 * https://github.com/li2
 */
package me.li2.android.view.image

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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
        "centerCrop",
        "circleCrop",
        "fitCenter"
    ], requireAll = false)
    fun setImageUrl(view: ImageView,
                    src: String?,
                    fallbackImageUrl: String?,
                    placeHolder: Drawable?,
                    centerCrop: Boolean?,
                    circleCrop: Boolean?,
                    fitCenter: Boolean?) {
        val requestOptions = RequestOptions().apply {
            if (centerCrop.orFalse()) centerCrop()
            if (circleCrop.orFalse()) circleCrop()
            if (fitCenter.orFalse()) fitCenter()
            if (placeHolder != null) placeholder(placeHolder)
        }
        if (src != null) {
            Glide.with(view.context)
                    .load(src)
                    .error(Glide.with(view.context).load(fallbackImageUrl))
                    .apply(requestOptions)
                    .into(view)
        }
    }
}
