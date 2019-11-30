package sk.rafig.mhdke.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_active_ticket.*
import sk.rafig.mhdke.R
import sk.rafig.mhdke.api.Cache
import sk.rafig.mhdke.util.ContextTags
import sk.rafig.mhdke.viewmodel.ActiveTicketViewModel
import sk.rafig.mhdke.viewmodel.ViewModelFactory

class ActiveTicketActivity : AppCompatActivity() {
    private val handler = Handler()
    private lateinit var runnable: Runnable
    private lateinit var viewModel: ActiveTicketViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_active_ticket)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(application)).get(ActiveTicketViewModel::class.java)

        id_activity_active_history_button.setOnClickListener {
            startActivity(Intent(applicationContext, HistoryActivity::class.java))
        }


        runnable = Runnable {
            if (Cache.getBoolean(ContextTags.TICKET_RECEIVED, application)) {
                // TODO received
//                 id_activity_ticket_time_remaining.setText(viewModel.formatText(viewModel.getTime()))

            }


            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)

    }
}
