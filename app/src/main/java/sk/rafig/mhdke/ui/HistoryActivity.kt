package sk.rafig.mhdke.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*
import sk.rafig.mhdke.viewmodel.HistoryViewModel
import sk.rafig.mhdke.R
import sk.rafig.mhdke.ui.adapter.HistoryRecyclerView
import sk.rafig.mhdke.ui.toolbar.CustomToolbar
import sk.rafig.mhdke.ui.toolbar.ToolbarColor
import sk.rafig.mhdke.util.Animator
import sk.rafig.mhdke.viewmodel.ViewModelFactory

class HistoryActivity : AppCompatActivity() {

    private lateinit var viewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(findViewById(R.id.id_activity_history_toolbar))
        CustomToolbar.createToolbarWithBackButton(this, ToolbarColor.WHITE, TicketActivity::class.java)

        Animator.emptyAnimation(id_activity_history_tickets_tumbleweed, 2500)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(application)).get(HistoryViewModel::class.java)

        viewModel.getTickets().observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                activity_history_recycler_view.visibility = View.VISIBLE
                id_activity_history_tickets_none.visibility = View.GONE
                activity_history_recycler_view.layoutManager = LinearLayoutManager(applicationContext)
                activity_history_recycler_view.adapter = HistoryRecyclerView(applicationContext, it)
            } else {
                activity_history_recycler_view.visibility = View.GONE
                id_activity_history_tickets_none.visibility = View.VISIBLE
            }
        })

    }
}
