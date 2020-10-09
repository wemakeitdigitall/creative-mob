package com.g7.webview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by G7 on 7/7/20.
 */

class MainActivity : Activity() {

    val TAG = "G7MainActivity"
    var webUrl = "https://makemoneynext.com"
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.startActivity(
            Intent(
                this,
                SplashActivity::class.java
            )
        )

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
        progressBar.visibility = View.VISIBLE
        val webSettings = activity_main_webview!!.settings
        webSettings.javaScriptEnabled = true
        activity_main_webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                Log.i(TAG, "loading URL: $url")

                if (progressBar.visibility == View.GONE) {
                    progressBar.visibility = View.VISIBLE
                }
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                Log.i(TAG, "Finished loading URL: $url")
                webUrl = url
                Log.i(TAG, "onPageFinished loading URL: $url")
                if (progressBar.isVisible) {
                    Handler().postDelayed({
                        progressBar.visibility = View.GONE
                    }, 2000)
                }
            }

            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                webUrl = failingUrl
                Log.e(TAG, "onReceivedError loading URL: $description")
                Log.e(TAG, "Error: $description")
                goToReconnectPage()
            }
        }
        activity_main_webview.loadUrl(webUrl)

    }

    private fun goToReconnectPage() {
        startActivityForResult(Intent(this, NoInternetActivity::class.java), 100)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action === KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (activity_main_webview.canGoBack()) {
                        activity_main_webview.goBack()
                    } else {
                        if (doubleBackToExitPressedOnce) {
                            super.onBackPressed()
                            return false
                        }

                        this.doubleBackToExitPressedOnce = true
                        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT)
                            .show()

                        Handler().postDelayed(
                            Runnable { doubleBackToExitPressedOnce = false },
                            2000
                        )
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK)
            if (progressBar.visibility == View.GONE) {
                progressBar.visibility = View.VISIBLE
            }
        activity_main_webview.loadUrl(webUrl)

    }
}
