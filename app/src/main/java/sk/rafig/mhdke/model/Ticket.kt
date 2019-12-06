package sk.rafig.mhdke.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Ticket (
    @PrimaryKey
    @ColumnInfo(name = "ticketId")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "boughtOn")
    val boughtOn: String = "",

    @ColumnInfo(name = "userId")
    val userId: String = "",

    @ColumnInfo(name = "column_body")
    val columnBody: String = "",

    @ColumnInfo(name = "price")
    val price: String = "",

    @ColumnInfo(name = "valid_from")
    val validFrom: Long = 0L,

    @ColumnInfo(name = "valid_till")
    val validTill: Long = 0L,

    @ColumnInfo(name = "ticket_code")
    val ticketCode: String = ""
)
