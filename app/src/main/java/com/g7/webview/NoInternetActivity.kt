package com.g7.webview

import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import kotlinx.android.synthetic.main.activity_no_internet.*

class NoInternetActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_internet)

        retry.setOnClickListener {
            finish()
        }

        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                autoRecconect.text = "${millisUntilFinished / 1000} sec"
            }

            override fun onFinish() {
                finish()
            }
        }.start()
    }
}
