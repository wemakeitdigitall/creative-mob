package com.g7.webview

import android.app.Activity
import android.os.Bundle
import android.os.Handler

/**
 * Created by G7 on 7/7/20.
 */

class SplashActivity : Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            this.finish()
        }, 3000)
    }
}