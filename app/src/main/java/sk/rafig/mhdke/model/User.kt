package sk.rafig.mhdke.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class User (

    @PrimaryKey
    @ColumnInfo(name = "userId")
//    val id: String = UUID.randomUUID().toString(),
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "username")
    val userName: String

//    @ColumnInfo(name = " ticket_list")
//    val tickets: List<Ticket>
)
