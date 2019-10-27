package sk.rafig.mhdke.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import sk.rafig.mhdke.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addUser(user: User): Completable

    @Query("SELECT * FROM Users WHERE userId = :id")
    fun getUser(id: String): Single<User>
}