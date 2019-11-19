package sk.rafig.mhdke.ui

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_ticket.*
import org.hotovo.mhdke.viewmodel.TicketViewModel
import sk.rafig.mhdke.R
import sk.rafig.mhdke.viewmodel.ViewModelFactory

class TicketActivity : AppCompatActivity() {

    private lateinit var viewModel: TicketViewModel
    private lateinit var runnable: Runnable
    private var handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(application)).get(TicketViewModel::class.java)


        viewModel.getUser().observe(this, Observer{
            Log.d(this.javaClass.simpleName, it.userName)
            ticket_time_remaining.setText(it.userName)

        })

        buy_ticket.setOnClickListener {
            viewModel.sendSms()
            ticket_button_text.visibility = View.GONE
            ticket_button_loading.visibility = View.VISIBLE
        }

        runnable = Runnable {
            val value = viewModel.receiveSms()
            Log.d("VALUE", value)
            if (!value.equals("ERROR")) {
                ticket_button_loading.visibility = View.GONE
                ticket_time_remaining.setText("60:00")
            }
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)



    }
}
