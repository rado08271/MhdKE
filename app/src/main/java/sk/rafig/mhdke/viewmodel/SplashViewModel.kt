package sk.rafig.mhdke.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Single
import sk.rafig.mhdke.api.Cache
import sk.rafig.mhdke.api.UserDao
import sk.rafig.mhdke.api.UsersDatabase
import sk.rafig.mhdke.di.Injection
import sk.rafig.mhdke.model.User
import sk.rafig.mhdke.ui.AllowActivity
import sk.rafig.mhdke.ui.LegalActivity
import sk.rafig.mhdke.ui.WelcomeActivity
import sk.rafig.mhdke.util.ContextTags

class SplashViewModel(private val application: Application): ViewModel() {
    private var userDatabase: UserDao = Injection.proviceUserDataSource(application.applicationContext)

    fun createUser(): Completable{
        Cache.addValueToCache(ContextTags.USER_EXIST, true, application)
        return userDatabase.addUser(User(userName = "user"))
    }

    fun getUser(): Single<User> {
        return userDatabase.getUser("0000")
    }

    fun splash(){
        if (!Cache.getBoolean(ContextTags.USER_SEEN_WELCOME, application)) {
            application.startActivity(Intent(application.applicationContext, WelcomeActivity::class.java))
        } else if (!Cache.getBoolean(ContextTags.USER_SEEN_LEGAL, application)) {
            application.startActivity(Intent(application.applicationContext, LegalActivity::class.java))
        } else {
            application.startActivity(Intent(application.applicationContext, AllowActivity::class.java))
        }
    }
}