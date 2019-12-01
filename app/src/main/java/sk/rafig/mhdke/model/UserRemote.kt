package sk.rafig.mhdke.model

import android.os.Build
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class UserRemote (

    @PrimaryKey
    @ColumnInfo(name = "userId")
    val id: String = UUID.randomUUID().toString(),
//    val id: String = ,

    @ColumnInfo(name = "username")
    val userName: String = Build.VERSION.CODENAME + "|" +
            Build.BOARD + "|" +  Build.BOARD + "|" +
            Build.DEVICE + "|" + Build.DISPLAY + "|" +
            Build.MANUFACTURER + "|" + Build.PRODUCT,

    @ColumnInfo(name = "ticket_list")
    val tickets: List<Ticket> = listOf()
)
