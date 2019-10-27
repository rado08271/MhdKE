package sk.rafig.mhdke.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.hotovo.mhdke.ui.WelcomeActivity
import sk.rafig.mhdke.R
import sk.rafig.mhdke.model.User
import sk.rafig.mhdke.viewmodel.LoadingViewModel


class LoadingActivity : AppCompatActivity() {

    private lateinit var viewModel: LoadingViewModel

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        viewModel = ViewModelProviders.of(this).get(LoadingViewModel::class.java)
        viewModel.setContext(applicationContext)
    }

    override fun onStart() {
        super.onStart()

        disposable.add(viewModel.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {startActivity(Intent(applicationContext, WelcomeActivity::class.java))},
                {generateUser()}
            ))
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
