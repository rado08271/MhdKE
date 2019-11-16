package sk.rafig.mhdke.ui

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_ticket.*
import org.hotovo.mhdke.viewmodel.TicketViewModel
import sk.rafig.mhdke.R
import sk.rafig.mhdke.viewmodel.ViewModelFactory

class TicketActivity : AppCompatActivity() {

    private lateinit var viewModel: TicketViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(application)).get(TicketViewModel::class.java)

        buy_ticket.setOnClickListener {
            viewModel.sendSms()
            buy_ticket.visibility = View.GONE
        }
    }
}
