package sk.rafig.mhdke.api

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import sk.rafig.mhdke.model.User

interface UserService {
    fun addUser(user: User)
    fun getUser(id: String): User

}