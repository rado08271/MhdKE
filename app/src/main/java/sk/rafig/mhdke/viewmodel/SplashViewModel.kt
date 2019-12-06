package sk.rafig.mhdke.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sk.rafig.mhdke.api.*
import sk.rafig.mhdke.api.local.Cache
import sk.rafig.mhdke.api.local.UserRepository
import sk.rafig.mhdke.di.Injection
import sk.rafig.mhdke.model.User
import sk.rafig.mhdke.model.UserRemote
import sk.rafig.mhdke.ui.*
import sk.rafig.mhdke.util.ContextTags

class SplashViewModel(private val application: Application): ViewModel() {
    private var userRepository: UserRepository =
        UserRepository(Injection.proviceUserDataSource(application.applicationContext))
    private lateinit var user: LiveData<User>

    private fun createUser(){
        viewModelScope.launch {
            val userRemote = UserRemote(tickets = listOf())
            val user = User(id = userRemote.id)

            userRepository.createPerson(user)
            UserServiceFirebase.addUser(userRemote)

            Cache.addValueToCache(ContextTags.USER_EXIST, true, application)
            Cache.addValueToCache(ContextTags.USER_ID, user.id, application)
        }
    }

    fun splash(): LiveData<Class<*>> {
        if (!Cache.getBoolean(ContextTags.USER_EXIST, application)) {
            createUser()
        }

        return if (!Cache.getBoolean(ContextTags.USER_SEEN_WELCOME, application)) {
            MutableLiveData<Class<*>>(WelcomeActivity::class.java)
        } else if (!Cache.getBoolean(ContextTags.USER_SEEN_LEGAL, application)) {
            MutableLiveData<Class<*>>(LegalActivity::class.java)
        } else {
            MutableLiveData<Class<*>>(AllowActivity::class.java)
        }
    }
}