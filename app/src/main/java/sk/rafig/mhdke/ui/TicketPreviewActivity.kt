package sk.rafig.mhdke.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_ticket_preciew.*
import kotlinx.android.synthetic.main.fragment_ticket.*
import org.hotovo.mhdke.viewmodel.TicketPreviewViewModel
import sk.rafig.mhdke.R
import sk.rafig.mhdke.ui.toolbar.Toolbar
import sk.rafig.mhdke.ui.toolbar.ToolbarColor
import sk.rafig.mhdke.util.Animator
import sk.rafig.mhdke.util.Barcoder
import sk.rafig.mhdke.util.ContextTags
import sk.rafig.mhdke.viewmodel.SplashViewModel
import sk.rafig.mhdke.viewmodel.ViewModelFactory

class TicketPreviewActivity : AppCompatActivity() {

    private lateinit var viewModel: TicketPreviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_preciew)
        setActionBar(findViewById(R.id.id_activity_active_ticket_preview_toolbar))
        Toolbar.createToolbarWithBackButton(this, ToolbarColor.WHITE, HistoryActivity::class.java)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(application))
            .get(TicketPreviewViewModel::class.java)

        val ticketId = intent.getStringExtra(ContextTags.HISTORY_TICKET_ID)

        if (ticketId != null) {
            hideLoading()
            viewModel.getTicket(ticketId).observe(this, Observer {
                if (it != null) {
                    hideLoading()

                    id_activity_active_ticket_preview_fragmentreceived_ticket_message.text = it.columnBody
                    id_activity_active_ticket_preview_fragmentreceived_ticket_number.text = it.id
                    id_activity_active_ticket_preview_fragmentreceived_ticket_code
                        .setImageBitmap(Barcoder.createBarcodeFromString(it.id))
                } else {
                    showLoading()
                }
            })
        } else {
            showLoading()
        }
    }

    fun showLoading(){
        id_activity_preview_tickets_none.visibility = View.VISIBLE
        Animator().emptyAnimation(id_activity_preview_tickets_tumbleweed, 2500, 0f)
    }

    fun hideLoading(){
        id_activity_preview_tickets_none.visibility = View.GONE
        Animator().stopAnimation(id_activity_preview_tickets_tumbleweed)
    }
}
