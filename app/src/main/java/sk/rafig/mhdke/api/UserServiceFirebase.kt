package sk.rafig.mhdke.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import sk.rafig.mhdke.model.Ticket
import sk.rafig.mhdke.model.User
import sk.rafig.mhdke.model.UserRemote

object UserServiceFirebase {

    private val ref = FirebaseDatabase.getInstance().getReference("data")
    private var user = MutableLiveData<UserRemote>()
    private var ticket = MutableLiveData<Ticket>()
    private var tickets = MutableLiveData<List<Ticket>>()

    fun addUser(user: UserRemote){
        ref.child(user.id).setValue(user)
    }

    fun getUser(id: String): LiveData<UserRemote>{
        ref.child(id).addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                user.value = p0.getValue(UserRemote::class.java)
            }
        })
        return user
    }

     fun addTicket(userId: String, ticket: Ticket){
        ref.child(userId).child("ticket_list").child(ticket.id).setValue(ticket)

    }

     fun getTicket(userId: String, id: String): LiveData<Ticket>{
        ref.child(userId).child("ticket_list").child(id).addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                ticket.value = p0.getValue(Ticket::class.java)
            }
        })
        return ticket

    }

     fun getAllTickets(userId: String): LiveData<List<Ticket>> {
        ref.child(userId).child("ticket_list").addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val list:ArrayList<Ticket> = arrayListOf()
                for (s in p0.children) {
                    val tiket: Ticket = s.getValue(Ticket::class.java)!!
                    list.add(tiket)
                }

                tickets.value = list
            }
        })
        return tickets

    }
}