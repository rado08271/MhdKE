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
import sk.rafig.mhdke.api.Cache
import sk.rafig.mhdke.model.User
import sk.rafig.mhdke.viewmodel.LoadingViewModel


class LoadingActivity : AppCompatActivity() {

    private lateinit var viewModel: LoadingViewModel

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        viewModel = ViewModelProviders.of(this).get(LoadingViewModel::class.java)
        val cache = Cache(applicationContext)

        if (!viewModel.getUser(cache).userName.equals("NULL")) {
            // TODO get all tickets to database...
            startActivity(Intent(applicationContext, WelcomeActivity::class.java))
        }

        if (cache.seenOnBoarding()){
            startActivity(Intent(applicationContext, AllowActivity::class.java))
        }

//        viewModel.createUser(cache)
    }

}
