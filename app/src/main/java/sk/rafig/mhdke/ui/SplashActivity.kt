package sk.rafig.mhdke.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import sk.rafig.mhdke.R
import sk.rafig.mhdke.viewmodel.SplashViewModel
import sk.rafig.mhdke.viewmodel.ViewModelFactory


class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(application))
            .get(SplashViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()

        disposable.add(viewModel.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {startActivity(Intent(applicationContext, WelcomeActivity::class.java))},
                {generateUser()}
            ))
        viewModel.splash()
    }

    fun generateUser(){
        disposable.add(viewModel.createUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( { Log.d("LOADING", "User added") /*TODO also add to server*/} ,
                { error -> Log.e("LOADING", "Unable to create user", error)}
            ))
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }
}
