package sk.rafig.mhdke.ui

import android.content.Context
import android.content.DialogInterface
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
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_ticket.*
import kotlinx.android.synthetic.main.dialog.*
import kotlinx.android.synthetic.main.dialog.view.*
import sk.rafig.mhdke.viewmodel.TicketViewModel
import sk.rafig.mhdke.R
import sk.rafig.mhdke.api.local.Cache
import sk.rafig.mhdke.api.service.SmsReciever
import sk.rafig.mhdke.ui.toolbar.CustomToolbar
import sk.rafig.mhdke.ui.toolbar.ToolbarColor
import sk.rafig.mhdke.util.Animator
import sk.rafig.mhdke.util.ContextTags
import sk.rafig.mhdke.viewmodel.ViewModelFactory

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
        setSupportActionBar(findViewById(R.id.id_activity_ticket_toolbar))
        CustomToolbar.createToolbar(this, ToolbarColor.BLACK, false)

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

        id_activity_ticket_ticket_buy_ticket.setOnClickListener {
            if (!Cache.getBoolean(ContextTags.SHOW_DIALOG, application)) {
                val builder = AlertDialog.Builder(this)
                val view = layoutInflater.inflate(R.layout.dialog, null)
                builder.setTitle(applicationContext.getString(R.string.string_dialog_title))
                    .setView(view)
                    .setCancelable(true)
                    .setPositiveButton(applicationContext.getString(R.string.string_dialog_ok),
                        DialogInterface.OnClickListener { dialog, id ->
                            if (view.id_dialog_checked.isChecked) {
                                Cache.addValueToCache(ContextTags.SHOW_DIALOG, true, application)
                            }
                            viewModel.sendSms()
                            waiting()
                        })
                    .setNegativeButton(applicationContext.getString(R.string.string_dialog_cancel),
                        DialogInterface.OnClickListener { dialog, id ->
                            dialog.cancel()
                            dialog.dismiss()

                        })
                    .create()
                    .show()
            } else {
                viewModel.sendSms()
                waiting()
            }

        }

        id_activity_ticket_history_button.setOnClickListener {
            startActivity(Intent(applicationContext, HistoryActivity::class.java))
        }

        runnable = Runnable {
            receiver.setOnReceiveListener {
                if (!Cache.getBoolean(ContextTags.TICKET_RECEIVED, application)) {
                    id_activity_ticket_button_image.visibility = View.INVISIBLE
                    Animator.scaleAnimation(id_activity_ticket_background_view_animation, 1000)
                    viewModel.addTicet(it)
                }
            }

            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }


    private fun toBuy() {
        Animator.pulseAnimation(id_activity_ticket_primary_pulse, 1000)
        Animator.stopAnimation(id_activity_ticket_secondary_pulse)
        id_activity_ticket_sms_title.setText(R.string.ticket_sms_price)
        id_activity_ticket_ticket_buy_ticket.visibility = View.VISIBLE
        id_activity_ticket_loading_button.visibility = View.GONE
        id_activity_ticket_primary_pulse.visibility = View.VISIBLE
        id_activity_ticket_secondary_pulse.visibility = View.INVISIBLE
        id_activity_ticket_sms_title.visibility = View.VISIBLE
    }

    private fun waiting() {
        Animator.pulseAnimation(id_activity_ticket_primary_pulse, 1000)
        Animator.pulseAnimation(id_activity_ticket_secondary_pulse, 1200)
        id_activity_ticket_sms_title.setText(R.string.string_activity_ticket_ticket_waiting_for)
        id_activity_ticket_ticket_buy_ticket.visibility = View.GONE
        id_activity_ticket_loading_button.visibility = View.VISIBLE
        id_activity_ticket_primary_pulse.visibility = View.VISIBLE
        id_activity_ticket_secondary_pulse.visibility = View.VISIBLE
        id_activity_ticket_sms_title.visibility = View.VISIBLE
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
