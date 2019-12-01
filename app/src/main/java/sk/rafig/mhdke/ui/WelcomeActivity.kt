package sk.rafig.mhdke.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_welcome.*
import org.hotovo.mhdke.viewmodel.WelcomeViewModel
import sk.rafig.mhdke.R
import sk.rafig.mhdke.ui.toolbar.Toolbar
import sk.rafig.mhdke.ui.toolbar.ToolbarColor
import sk.rafig.mhdke.viewmodel.ViewModelFactory

class WelcomeActivity : AppCompatActivity() {

    private lateinit var viewModel: WelcomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        setActionBar(findViewById(R.id.id_activity_welcome_toolbar))
        Toolbar.createToolbar(this, ToolbarColor.BLACK, false)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(application)).get(WelcomeViewModel::class.java)

        welcome_continue.setOnClickListener{
            viewModel.welcomeSeen()
            startActivity(Intent(applicationContext, LegalActivity::class.java))
        }
    }
}
