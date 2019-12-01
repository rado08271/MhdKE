package sk.rafig.mhdke.api.local

import androidx.lifecycle.LiveData
import sk.rafig.mhdke.model.User

class UserRepository(private val userDao: UserDao) {
    lateinit var user: LiveData<User>

    suspend fun createPerson(user: User){
        userDao.addUser(user)
    }

    fun getPerson(id: String): LiveData<User> {
        user = userDao.getUser(id)
        return user
    }
}