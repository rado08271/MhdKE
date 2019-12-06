package sk.rafig.mhdke.ui

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_current_ticket_preview.*
import sk.rafig.mhdke.R
import sk.rafig.mhdke.model.Ticket
import sk.rafig.mhdke.ui.toolbar.CustomToolbar
import sk.rafig.mhdke.ui.toolbar.ToolbarColor
import sk.rafig.mhdke.util.Animator
import sk.rafig.mhdke.util.Barcoder
import sk.rafig.mhdke.viewmodel.CurrentTicketViewModel
import sk.rafig.mhdke.viewmodel.ViewModelFactory
import java.sql.Timestamp
import java.util.*

class CurrentTicketPreview : AppCompatActivity() {

    private lateinit var viewModel: CurrentTicketViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_ticket_preview)
        setSupportActionBar(findViewById(R.id.id_current_preview_toolbar))
        CustomToolbar.createToolbar(this, ToolbarColor.WHITE, false)

        id_current_preview_ticket_cancel.setOnClickListener {
            startActivity(Intent(applicationContext, SplashActivity::class.java))
        }

        viewModel = ViewModelProviders.of(this, ViewModelFactory(application))
            .get(CurrentTicketViewModel::class.java)

        viewModel.getTicket().observe(this, Observer {
            hideLoading()
            fillData(it)
        })
    }

    private fun showLoading() {
        id_current_preview_tickets_none.visibility = View.VISIBLE
        Animator.emptyAnimation(id_current_preview_tickets_tumbleweed, 2500)
    }

    private fun hideLoading() {
        id_current_preview_tickets_none.visibility = View.GONE
        Animator.stopAnimation(id_current_preview_tickets_tumbleweed)
    }

    private fun fillData(it: Ticket) {
        if (it != null) {
            hideLoading()

            id_current_preview_code.text = it.columnBody
            id_current_preview_type.text = it.id
            id_current_preview_price.text = it.price
            id_current_preview_code.text = it.ticketCode


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                id_current_preview_valid_from.text =
                    SimpleDateFormat("dd.MM.yyyy - HH:mm:ss").format(Date(it.validFrom))
                id_current_preview_valid_till.text =
                    SimpleDateFormat("dd.MM.yyyy - HH:mm:ss").format(Date(it.validTill))

            } else {

                id_current_preview_valid_from.text = Timestamp(it.validFrom).time.toString()
                id_current_preview_valid_till.text = Timestamp(it.validTill).time.toString()
                id_current_preview_received_ticket_QR.setImageBitmap(
                    Barcoder.createQrCodeFromString(
                        it.id
                    )
                )
            }
        } else {
            showLoading()
        }
    }
}
