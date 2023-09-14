package com.example.dt_test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient

class WebFragment : Fragment() {
    private val webView by lazy {
        view?.findViewById<WebView>(R.id.myWeb)
    }
    private var url  = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        url = arguments?.getString("URL") ?: ""
        return inflater.inflate(R.layout.fragment_web, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWebView(savedInstanceState)
    }

    private fun setupWebView(savedInstanceState: Bundle?) {
        webView?.webViewClient = WebViewClient()

        val webSettings = webView?.settings
        webSettings?.let {
            webSettings.javaScriptEnabled = true
            webSettings.domStorageEnabled = true
            webSettings.javaScriptCanOpenWindowsAutomatically = true
//        webSettings.javaScriptEnabled = true
            webSettings.loadWithOverviewMode = true
            webSettings.useWideViewPort = true
            webSettings.domStorageEnabled = true
            webSettings.databaseEnabled = true
            webSettings.setSupportZoom(false)
            webSettings.allowFileAccess = true
            webSettings.allowContentAccess = true
            webSettings.loadWithOverviewMode = true
            webSettings.useWideViewPort = true
        }

        if (savedInstanceState != null) {
            webView?.restoreState(savedInstanceState)
        } else {
            webView?.loadUrl(url!!)
        }

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
    }
}