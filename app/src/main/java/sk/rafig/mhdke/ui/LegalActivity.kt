package sk.rafig.mhdke.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_legal.*
import sk.rafig.mhdke.R
import sk.rafig.mhdke.ui.toolbar.CustomToolbar
import sk.rafig.mhdke.ui.toolbar.ToolbarColor
import sk.rafig.mhdke.viewmodel.LegalViewModel
import sk.rafig.mhdke.viewmodel.ViewModelFactory

class LegalActivity : AppCompatActivity() {

    private lateinit var viewModel: LegalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_legal)
        setSupportActionBar(findViewById(R.id.id_activity_legal_toolbar))
        CustomToolbar.createToolbar(this, ToolbarColor.BLACK, false)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(application)).get(LegalViewModel::class.java)

        legal_agree.setOnClickListener {
            viewModel.seenLegal()
            startActivity(Intent(applicationContext, AllowActivity::class.java))
        }
    }
}
