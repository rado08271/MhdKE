package sk.rafig.mhdke.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Single
import sk.rafig.mhdke.api.UserDao
import sk.rafig.mhdke.api.UsersDatabase
import sk.rafig.mhdke.di.Injection
import sk.rafig.mhdke.model.User

class LoadingViewModel(): ViewModel() {
    private lateinit var context: Context
    private lateinit var userDatabase: UserDao

    fun createUser(): Completable{
        return userDatabase.addUser(User(userName = "user"))
    }

    fun getUser(): Single<User> {

        return userDatabase.getUser("0000")
    }

    fun setContext(context: Context) {
        this.context = context
        userDatabase = Injection.proviceUserDataSource(context)

    }

    //add user also to server!!!
}