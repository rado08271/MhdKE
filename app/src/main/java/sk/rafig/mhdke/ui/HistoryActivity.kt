package sk.rafig.mhdke.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*
import org.hotovo.mhdke.viewmodel.HistoryViewModel
import sk.rafig.mhdke.R
import sk.rafig.mhdke.ui.adapter.HistoryRecyclerView
import sk.rafig.mhdke.viewmodel.ViewModelFactory

class HistoryActivity : AppCompatActivity() {

    private lateinit var viewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(application)).get(HistoryViewModel::class.java)

        viewModel.getTickets().observe(this, Observer {
            if (it != null && it.size > 0 ) {
                activity_history_recycler_view.layoutManager = LinearLayoutManager(applicationContext)
                activity_history_recycler_view.adapter = HistoryRecyclerView(applicationContext, it)
            } else {
                viewModel.getTickets()
                viewModel.fill()
            }
        })

    }
}
