package sk.rafig.mhdke.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import sk.rafig.mhdke.api.local.Cache
import sk.rafig.mhdke.api.local.UserRepository
import sk.rafig.mhdke.di.Injection
import sk.rafig.mhdke.model.User
import sk.rafig.mhdke.util.ContextTags

class ToolbarViewModel(val application: Application): ViewModel() {
    private var userRepository: UserRepository =
        UserRepository(Injection.proviceUserDataSource(application.applicationContext))

    fun getUser(): LiveData<User> {
        return userRepository.getPerson(Cache.getString(ContextTags.USER_ID, application))
    }
}