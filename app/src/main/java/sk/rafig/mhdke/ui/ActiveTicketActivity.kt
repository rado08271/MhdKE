package sk.rafig.mhdke.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_active_ticket.*
import sk.rafig.mhdke.R
import sk.rafig.mhdke.api.local.Cache
import sk.rafig.mhdke.model.Ticket
import sk.rafig.mhdke.notifications.service.NotificationService
import sk.rafig.mhdke.ui.toolbar.CustomToolbar
import sk.rafig.mhdke.ui.toolbar.ToolbarColor
import sk.rafig.mhdke.util.ContextTags
import sk.rafig.mhdke.util.SmsSpecs
import sk.rafig.mhdke.util.TimeUtil
import sk.rafig.mhdke.viewmodel.ActiveTicketViewModel
import sk.rafig.mhdke.viewmodel.ViewModelFactory
import kotlin.concurrent.thread

class ActiveTicketActivity : AppCompatActivity() {
    private val handler = Handler()
    private lateinit var runnable: Runnable
    private lateinit var viewModel: ActiveTicketViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_active_ticket)
        setSupportActionBar(findViewById(R.id.id_activity_active_toolbar))
        CustomToolbar.createToolbar(this, ToolbarColor.WHITE, false)
        NotificationService.startService(this)
        initProgress()

        viewModel = ViewModelProviders.of(this, ViewModelFactory(application))
            .get(ActiveTicketViewModel::class.java)

        viewModel.getUser().observe(this, Observer {
            Log.d(this.javaClass.simpleName, it.userName)
        })

        runOnUiThread {
            viewModel.getTicket().observe(this, Observer {
                id_activity_active_image_background.setOnClickListener {
                    if (it != null) {
                        NotificationService.stopService(this)
                        handler.removeCallbacks(runnable)
                        startActivity(Intent(applicationContext,CurrentTicketPreview::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                        finish()
                    }
                }
            })

        }

        id_activity_active_history_button.setOnClickListener {
            startActivity(Intent(applicationContext, HistoryActivity::class.java))
        }

        thread {
            runnable = Runnable {
                if (Cache.getBoolean(ContextTags.TICKET_RECEIVED, application)) {
                    // TODO received
                    viewModel.getTime().observe(this, Observer {
                        if (it < 0) {
                            viewModel.ticketExpired()
                        }

                        id_activity_active_time_remaining.text = TimeUtil.formatText(it)
                        id_activity_active_progress.progress = it.toInt()
                    })
                }

                handler.postDelayed(runnable, 1000)
            }
            handler.postDelayed(runnable, 1000)

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(applicationContext, SplashActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        NotificationService.stopService(this)
        handler.removeCallbacks(runnable)
    }

    private fun initProgress() {
        id_activity_active_progress.max = SmsSpecs.length.toInt()
    }

    private fun showTicket(ticket: Ticket) {
    }
}
