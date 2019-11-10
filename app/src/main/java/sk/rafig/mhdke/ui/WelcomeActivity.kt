package sk.rafig.mhdke.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_welcome.*
import sk.rafig.mhdke.R
import sk.rafig.mhdke.api.Cache

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val cache = Cache(applicationContext)

        welcome_continue.setOnClickListener{
            cache.onboardingDone()
            startActivity(Intent(applicationContext, AllowActivity::class.java))
        }
    }
}
