package sk.rafig.mhdke.api

import com.google.firebase.database.FirebaseDatabase

// TODO Use for setting for the app!!!
object AppServiceFirebase {
    private val ref = FirebaseDatabase.getInstance().getReference("app")

}