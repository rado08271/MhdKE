package sk.rafig.mhdke.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_oops.*
import sk.rafig.mhdke.R
import sk.rafig.mhdke.ui.toolbar.Toolbar
import sk.rafig.mhdke.ui.toolbar.ToolbarColor

class OopsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oops)
        setActionBar(findViewById(R.id.id_activity_oops_toolbar))
        Toolbar.createToolbar(this, ToolbarColor.BLACK, false)

        oops_allow_access.setOnClickListener {
            startActivity(Intent(applicationContext, AllowActivity::class.java))
        }

        close_app.setOnClickListener {
            finish()
            moveTaskToBack(true)
            finishAffinity()
            finishAndRemoveTask()
        }
    }


}
