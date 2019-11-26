package sk.rafig.mhdke.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.launch
import sk.rafig.mhdke.api.Cache
import sk.rafig.mhdke.api.UserDao
import sk.rafig.mhdke.api.UserRepository
import sk.rafig.mhdke.api.UsersDatabase
import sk.rafig.mhdke.di.Injection
import sk.rafig.mhdke.model.User
import sk.rafig.mhdke.ui.AllowActivity
import sk.rafig.mhdke.ui.HistoryActivity
import sk.rafig.mhdke.ui.LegalActivity
import sk.rafig.mhdke.ui.WelcomeActivity
import sk.rafig.mhdke.util.ContextTags

class SplashViewModel(private val application: Application): ViewModel() {
    private var userRepository: UserRepository = UserRepository(Injection.proviceUserDataSource(application.applicationContext))
    private lateinit var user: LiveData<User>

    fun createUser(){
        viewModelScope.launch {
            Log.d("USER", "CREATING USER")
            val user = User(userName = "user")
            userRepository.createPerson(user)
            Cache.addValueToCache(ContextTags.USER_EXIST, true, application)
            Cache.addValueToCache(ContextTags.USER_ID, user.id, application)
        }
    }

    fun splash(){
        if (!Cache.getBoolean(ContextTags.USER_EXIST, application)) {
            createUser()
        }

        if (!Cache.getBoolean(ContextTags.USER_SEEN_WELCOME, application)) {
            application.startActivity(Intent(application.applicationContext, WelcomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        } else if (!Cache.getBoolean(ContextTags.USER_SEEN_LEGAL, application)) {
            application.startActivity(Intent(application.applicationContext, LegalActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        } else {
            application.startActivity(Intent(application.applicationContext, HistoryActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }
}