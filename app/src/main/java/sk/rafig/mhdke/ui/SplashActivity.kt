package sk.rafig.mhdke.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_splash.*
import sk.rafig.mhdke.R
import sk.rafig.mhdke.viewmodel.SplashViewModel
import sk.rafig.mhdke.viewmodel.ViewModelFactory


class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(application))
            .get(SplashViewModel::class.java)

        viewModel.splash().observe(this, Observer {
            if (it != null) {
                startActivity(Intent(applicationContext, it))
            }
        })

        id_splash_main_splash.setOnClickListener {
            viewModel.splash().observe(this, Observer {
                if (it != null) {
                    startActivity(Intent(applicationContext, it))
                }
            })
        }


    }

    override fun onResume() {
        super.onResume()
        onRestart()
    }
}
