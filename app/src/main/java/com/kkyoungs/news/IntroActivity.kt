package com.kkyoungs.news

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashHandler = Handler()
        splashHandler.postDelayed(Runnable {
            startActivity(Intent(this, NewsListActivity::class.java))
            finish()
        },1300)
    }
}