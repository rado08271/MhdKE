package sk.rafig.mhdke.api

import sk.rafig.mhdke.model.User

interface UserService {
    fun addUser(user: User)
    fun getUser(id: String): User

}