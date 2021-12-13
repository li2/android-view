/*
 * Created by Weiyi Li on 2019-11-03.
 * https://github.com/li2
 */
@file:Suppress("unused")
package me.li2.android.view.web

import android.annotation.SuppressLint
import android.content.Context
import android.net.http.SslError
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.webkit.*
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import me.li2.android.view.R
import me.li2.android.view.databinding.AdvancedWebviewBinding

/**
 * AdvancedWebView to support url, cookies, additional headers, custom view, loading spinner.
 */
class AdvancedWebView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr), LifecycleObserver {

    private lateinit var binding: AdvancedWebviewBinding
    private var customView: View? = null
    private var customViewCallback: WebChromeClient.CustomViewCallback? = null

    private val customizedWebChromeClient by lazy {
        object : WebChromeClient() {
            override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                super.onShowCustomView(view, callback)
                customView?.let {
                    callback?.onCustomViewHidden()
                    return
                }
                customView = view
                customViewCallback = callback
                binding.webview.visibility = View.GONE
                binding.customViewContainer.visibility = View.VISIBLE
                binding.customViewContainer.addView(view)
            }

            override fun onHideCustomView() {
                super.onHideCustomView()
                if (customView == null) {
                    return
                }
                // show original webview
                binding.webview.visibility = View.VISIBLE

                // hide the custom view and remove it from its container
                customView?.visibility = View.GONE
                binding.customViewContainer.removeView(customView)
                binding.customViewContainer.visibility = View.GONE
                customViewCallback?.onCustomViewHidden()
                customView = null
            }
        }
    }

    init {
        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.advanced_webview, this, true)
        binding.webview.apply {
            webChromeClient = customizedWebChromeClient
            // enable java script otherwise the UbiStore cannot show correctly
            settings.javaScriptEnabled = true
        }
    }

    fun loadUrl(url: String, additionalHttpHeaders: Map<String, String>? = null) {
        binding.isLoading = true
        if (additionalHttpHeaders == null) {
            binding.webview.loadUrl(url)
        } else {
            binding.webview.loadUrl(url, additionalHttpHeaders)
        }
        binding.webview.apply {
            webViewClient = object : WebViewClient() {
                override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                    binding.isLoading = false
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    binding.isLoading = false
                }

                override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                    // Ignore SSL certificate errors
                    handler?.proceed()
                }
            }
        }
    }

    fun setUserAgentString(userAgentString: String) {
        binding.webview.settings.userAgentString += userAgentString
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        binding.webview.onResume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onPause() {
        binding.webview.onPause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        binding.webview.destroy()
    }

    companion object {
        @JvmStatic
        @BindingAdapter(value = ["url", "additionalHttpHeaders"], requireAll = false)
        fun loadUrl(webView: AdvancedWebView,
                    url: String,
                    additionalHttpHeaders: Map<String, String>? = null) {
            webView.loadUrl(url, additionalHttpHeaders)
        }
    }
}
