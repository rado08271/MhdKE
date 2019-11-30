package sk.rafig.mhdke.ui

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Telephony
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_ticket.*
import org.hotovo.mhdke.viewmodel.TicketViewModel
import sk.rafig.mhdke.R
import sk.rafig.mhdke.api.Cache
import sk.rafig.mhdke.api.sms.SmsReciever
import sk.rafig.mhdke.model.Ticket
import sk.rafig.mhdke.util.Animator
import sk.rafig.mhdke.util.ContextTags
import sk.rafig.mhdke.viewmodel.ViewModelFactory
import java.util.*

class TicketActivity : AppCompatActivity() {
    private val TAG = this.javaClass.simpleName
    private val phoneNumber = "0908266949"
    private val smsBody = "hi"
    private val receiver = SmsReciever()
    private lateinit var viewModel: TicketViewModel
    private lateinit var runnable: Runnable
    private var handler = Handler()
    var value = "ERROR"
    var body = ""


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)
        registerReceiver(receiver, IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))

        viewModel = ViewModelProviders.of(this, ViewModelFactory(application))
            .get(TicketViewModel::class.java)


        viewModel.getUser().observe(this, Observer {
            Log.d(this.javaClass.simpleName, it.userName)
        })

        viewModel.ticketWaiting().observe(this, Observer {
            if (it) {
                startActivity(Intent(applicationContext, ActiveTicketActivity::class.java))
            } else {
                toBuy()
            }

            //what if still waiting ut closed the app?
        })


        viewModel.getTicket().observe(this, Observer {
            if (it != null) {
                Log.d(TAG, it.boughtOn)
                Log.d(TAG, it.id)
            }
        })

        runnable = Runnable {
            if (!Cache.getBoolean(ContextTags.TICKET_RECEIVED, application)) {
                receiver.setOnReceiveListener {
                    viewModel.addTicet(UUID.randomUUID().toString(), it)
                    Log.d(TAG, "HERE I AM")
                    startActivity(Intent(applicationContext, ActiveTicketActivity::class.java))
                    body = it
                }
            } else {
                Log.d(TAG, viewModel.getTime().toString())
            }

            Log.d(TAG, body)
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }



    fun toBuy() {
        Log.d(TAG, "YOU MAY BUY")
        id_activity_ticket_ticket_buy_ticket.setOnClickListener {
            viewModel.sendSms()
            waiting()
        }
        Animator().pulseAnimation(id_activity_ticket_primary_pulse, 1000)
        Animator().stopAnimation(id_activity_ticket_secondary_pulse)
        id_activity_ticket_sms_title.setText(R.string.ticket_sms_send)
        id_activity_ticket_ticket_buy_ticket.visibility = View.VISIBLE
        id_activity_ticket_loading_button.visibility = View.GONE
        id_activity_ticket_primary_pulse.visibility = View.VISIBLE
        id_activity_ticket_secondary_pulse.visibility = View.INVISIBLE
        id_activity_ticket_sms_title.visibility = View.VISIBLE
        id_activity_ticket_sms_price.visibility = View.VISIBLE
    }

    fun waiting() {
        Log.d(TAG, "WAITING")
        Animator().pulseAnimation(id_activity_ticket_primary_pulse, 1000)
        Animator().pulseAnimation(id_activity_ticket_secondary_pulse, 1200)
        id_activity_ticket_sms_title.setText(R.string.string_activity_ticket_ticket_waiting_for)
        id_activity_ticket_ticket_buy_ticket.visibility = View.GONE
        id_activity_ticket_loading_button.visibility = View.VISIBLE
        id_activity_ticket_primary_pulse.visibility = View.VISIBLE
        id_activity_ticket_secondary_pulse.visibility = View.VISIBLE
        id_activity_ticket_sms_title.visibility = View.VISIBLE
        id_activity_ticket_sms_price.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
        handler.removeCallbacks(runnable)
    }
}
