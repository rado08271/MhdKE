package sk.rafig.mhdke.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import sk.rafig.mhdke.R
import sk.rafig.mhdke.api.UsersDatabase
import sk.rafig.mhdke.viewmodel.SplashViewModel
import sk.rafig.mhdke.viewmodel.ViewModelFactory


class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(application))
            .get(SplashViewModel::class.java)

        viewModel.splash()

    }
}
