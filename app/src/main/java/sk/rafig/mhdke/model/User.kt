package sk.rafig.mhdke.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "users")
data class User (

    @PrimaryKey
    @ColumnInfo(name = "userId")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "username")
    val userName: String,

    @ColumnInfo(name = "tickets")
    val tickets: List<Ticket>

)
