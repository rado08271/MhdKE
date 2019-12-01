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
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_ticket.*
import sk.rafig.mhdke.viewmodel.TicketViewModel
import sk.rafig.mhdke.R
import sk.rafig.mhdke.api.local.Cache
import sk.rafig.mhdke.api.service.SmsReciever
import sk.rafig.mhdke.ui.toolbar.Toolbar
import sk.rafig.mhdke.ui.toolbar.ToolbarColor
import sk.rafig.mhdke.util.Animator
import sk.rafig.mhdke.util.ContextTags
import sk.rafig.mhdke.util.SmsSpecs
import sk.rafig.mhdke.util.TimeUtil
import sk.rafig.mhdke.viewmodel.ViewModelFactory
import java.util.*

class TicketActivity : AppCompatActivity() {
    private val TAG = this.javaClass.simpleName
    private lateinit var receiver: SmsReciever
    private lateinit var viewModel: TicketViewModel
    private lateinit var runnable: Runnable
    private var handler = Handler()


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)
        setActionBar(findViewById(R.id.id_activity_ticket_toolbar))
        Toolbar.createToolbar(this, ToolbarColor.BLACK, false)

        receiver = SmsReciever()
        registerReceiver(receiver, IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))

        viewModel = ViewModelProviders.of(this, ViewModelFactory(application))
            .get(TicketViewModel::class.java)

        viewModel.ticketWaiting().observe(this, Observer {
            if (it) {
                startActivity(Intent(applicationContext, ActiveTicketActivity::class.java))
            } else {
                toBuy()
            }
            //what if still waiting ut closed the app?
        })

        id_activity_ticket_time_remaining.text = TimeUtil.formatText(SmsSpecs.length)

        id_activity_ticket_ticket_buy_ticket.setOnClickListener {
            viewModel.sendSms()
            waiting()
        }

        id_activity_ticket_history_button.setOnClickListener {
            startActivity(Intent(applicationContext, HistoryActivity::class.java))
        }

        runnable = Runnable {
            receiver.setOnReceiveListener {
                if (!Cache.getBoolean(ContextTags.TICKET_RECEIVED, application)) {
                    viewModel.addTicet(UUID.randomUUID().toString(), it)
                }
            }

            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }


    private fun toBuy() {
        Log.d(TAG, "YOU MAY BUY")

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

    private fun waiting() {
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

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(applicationContext, SplashActivity::class.java))
    }

}
