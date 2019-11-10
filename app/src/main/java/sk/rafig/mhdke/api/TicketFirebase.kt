package sk.rafig.mhdke.api

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import sk.rafig.mhdke.model.Ticket
import sk.rafig.mhdke.model.User

object TicketFirebase: TicketService {
    private val ticketReference = FirebaseDatabase.getInstance().getReference("ticket")
    private lateinit var ticket: Ticket
    private lateinit var tickets: ArrayList<Ticket>

    override fun getTicket(userId: String): List<Ticket> {
        ticketReference.child(userId).child("tickets").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                for ( ds in p0.children) {
                    tickets.add(ds.getValue(Ticket::class.java)!!)
                }
            }

            override fun onCancelled(p0: DatabaseError) {}
        })

        return tickets
    }

    override fun getTicket(userId: String, id: String): Ticket {
        ticketReference.child(userId).child("tickets").child(id).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                p0.getValue(Ticket::class.java)!!
            }

            override fun onCancelled(p0: DatabaseError) {}
        })

        return ticket
    }

    override fun addTicket(userId: String, ticket: Ticket) {
        ticketReference.child(userId).child("tickets").child(ticket.id).setValue(ticket)
    }
}