package sk.rafig.mhdke.ui

import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Telephony
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_ticket.*
import org.hotovo.mhdke.viewmodel.TicketViewModel
import sk.rafig.mhdke.R
import sk.rafig.mhdke.api.Cache
import sk.rafig.mhdke.api.sms.SmsReciever
import sk.rafig.mhdke.util.ContextTags
import sk.rafig.mhdke.viewmodel.ViewModelFactory
import java.util.*

class TicketActivity : AppCompatActivity() {

    private val phoneNumber = "0908266949"
    private val smsBody = "hi"
    private val receiver = SmsReciever(phoneNumber, smsBody)
    private lateinit var viewModel: TicketViewModel
    private lateinit var runnable: Runnable
    private var handler = Handler()
    var value = "ERROR"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)
        registerReceiver(receiver, IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))

        viewModel = ViewModelProviders.of(this, ViewModelFactory(application))
            .get(TicketViewModel::class.java)


        viewModel.getUser().observe(this, Observer {
            Log.d(this.javaClass.simpleName, it.userName)
        })

        buy_ticket.setOnClickListener {
            viewModel.sendSms()
            Log.d(this.javaClass.simpleName, "CLICKED!!!")
            ticket_button_text.visibility = View.GONE
            ticket_button_loading.visibility = View.VISIBLE
        }

        activity_ticket_history_button.setOnClickListener {
            startActivity(Intent(applicationContext, HistoryActivity::class.java))
        }
        activity_ticket_history_button.visibility = View.VISIBLE

        runnable = Runnable {
            if (!Cache.getBoolean(ContextTags.TICKET_RECEIVED, application)) {
//                receiver.setListener {
                    //                    if (it.equals(smsBody)) {
                    Log.d("WOW", "HERE I AM")
                    Cache.addValueToCache(
                        ContextTags.TIME_LEFT,
                        (Calendar.getInstance().time.time + 3600).toInt(),
                        application
                    )
                    value = ""


//                }
            }
            Cache.addValueToCache(ContextTags.TICKET_RECEIVED, true, application)

            Log.d("VALUE", value)
            if (!value.equals("ERROR")) {
                ticket_button_loading.visibility = View.GONE
                ticket_time_remaining.setText(
                    viewModel.formatText(
                        Cache.getInt(
                            ContextTags.TIME_LEFT,
                            application
                        ) - Calendar.getInstance().time.time.toInt()

                    )
                )
                activity_ticket_background.background =
                    applicationContext.resources.getDrawable(R.color.colorPrimary)

            }
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

}
