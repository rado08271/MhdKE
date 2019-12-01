package sk.rafig.mhdke.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_active_ticket.*
import sk.rafig.mhdke.R
import sk.rafig.mhdke.api.local.Cache
import sk.rafig.mhdke.notifications.service.NotificationService
import sk.rafig.mhdke.ui.toolbar.Toolbar
import sk.rafig.mhdke.ui.toolbar.ToolbarColor
import sk.rafig.mhdke.util.ContextTags
import sk.rafig.mhdke.util.SmsSpecs
import sk.rafig.mhdke.util.TimeUtil
import sk.rafig.mhdke.viewmodel.ActiveTicketViewModel
import sk.rafig.mhdke.viewmodel.ViewModelFactory

class ActiveTicketActivity : AppCompatActivity() {
    private val handler = Handler()
    private lateinit var runnable: Runnable
    private lateinit var viewModel: ActiveTicketViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_active_ticket)
        setActionBar(findViewById(R.id.id_activity_active_toolbar))
        Toolbar.createToolbar(this, ToolbarColor.WHITE, false)
        NotificationService.startService(this)
        initProgress()

        viewModel = ViewModelProviders.of(this, ViewModelFactory(application))
            .get(ActiveTicketViewModel::class.java)

        viewModel.getUser().observe(this, Observer {
            Log.d(this.javaClass.simpleName, it.userName)
        })

        id_activity_active_show_ticket.setOnClickListener {
            viewModel.getTicket().observe(this, Observer {
                if (it != null) {
                    id_activity_active_ticket_preview_fragment.visibility = View.VISIBLE
                    TicketFragment.createFragmentLight(this, it)
                }
            })
        }

        id_activity_active_ticket_fragment_close_fragment.setOnClickListener {
            id_activity_active_ticket_preview_fragment.visibility = View.GONE
        }

        id_activity_active_time_remaining.text = TimeUtil.formatText(SmsSpecs.length)
        id_activity_active_history_button.setOnClickListener {
            startActivity(Intent(applicationContext, HistoryActivity::class.java))
        }

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

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(applicationContext, SplashActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        NotificationService.stopService(this)
    }

    private fun initProgress(){
        id_activity_active_progress.max = SmsSpecs.length.toInt()
    }
}
