package sk.rafig.mhdke.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_legal.*
import sk.rafig.mhdke.R
import sk.rafig.mhdke.ui.toolbar.Toolbar
import sk.rafig.mhdke.ui.toolbar.ToolbarColor
import sk.rafig.mhdke.viewmodel.LegalViewModel
import sk.rafig.mhdke.viewmodel.ViewModelFactory

class LegalActivity : AppCompatActivity() {

    private lateinit var viewModel: LegalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_legal)
        setActionBar(findViewById(R.id.id_activity_legal_toolbar))
        Toolbar.createToolbar(this, ToolbarColor.BLACK, false)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(application)).get(LegalViewModel::class.java)

        legal_agree.setOnClickListener {
            viewModel.seenLegal()
            startActivity(Intent(applicationContext, AllowActivity::class.java))
        }

        legal_disagree.setOnClickListener {
            Toast.makeText(applicationContext, R.string.legal_toast_error, Toast.LENGTH_LONG).show()
        }
    }
}
