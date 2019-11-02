package sk.rafig.mhdke.api

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleObserver
import sk.rafig.mhdke.model.User


object UserFirebase: UserService {
    private val userReference = FirebaseDatabase.getInstance().getReference("user")
    private lateinit var user: User

    override fun addUser(user: User) {
        userReference.child(user.id).setValue(user)
    }

    override fun getUser(id: String): User {
        userReference.child(id).addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                user = User(id="ERROR", userName = "")
            }

            override fun onDataChange(p0: DataSnapshot) {
                user = p0.getValue(User::class.java)!!
            }

        })

        return user
    }

}