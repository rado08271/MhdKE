package sk.rafig.mhdke.viewmodel

import android.content.Context
import android.os.Build
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Single
import sk.rafig.mhdke.api.Cache
import sk.rafig.mhdke.api.UserFirebase
import sk.rafig.mhdke.model.User

class LoadingViewModel(): ViewModel() {
    private lateinit var context: Context
    private var userDatabase = UserFirebase

    fun createUser(cache: Cache) {
        if (cache.getUserId().equals("NULL") || cache.getUserId().equals("")){
            cache.createUserId("0001")
            val string = Build.BOARD + Build.BRAND + Build.MANUFACTURER + Build.MODEL
            userDatabase.addUser(User(userName = string, id = cache.getUserId(), tickets = emptyList()))
        }
    }

    fun getUser(cache: Cache): User {
        if (cache.getUserId().equals("NULL") || cache.getUserId().equals("")) {
            createUser(cache)
        }

        return userDatabase.getUser(cache.getUserId())
    }

    //add user also to cache!!!!!!!
}