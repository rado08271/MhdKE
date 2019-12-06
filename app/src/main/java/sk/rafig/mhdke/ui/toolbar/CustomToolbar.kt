package sk.rafig.mhdke.ui.toolbar

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.toolbar.*
import sk.rafig.mhdke.viewmodel.ToolbarViewModel
import sk.rafig.mhdke.viewmodel.ViewModelFactory

class CustomToolbar {
    companion object {

        fun createToolbar(view: AppCompatActivity, color: ToolbarColor, back: Boolean) {
            view.the_toolbar_logo.setImageDrawable(
                view.applicationContext.getDrawable(color.drawable)
            )

            //nothing to do for now!
            val viewModel = ViewModelProviders.of(
                view,
                ViewModelFactory(view.application)
            ).get(ToolbarViewModel::class.java)

            view.the_toolbar_logo.setOnClickListener {
                viewModel.getUser().observe(view, Observer {
                    if (it != null) {
                        Toast.makeText(
                            view.applicationContext,
                            it.userName + " \nyour fingerprint: " + it.id,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }

            if (back) {
                view.the_toolbar_back_button.visibility = View.VISIBLE
            } else {
                view.the_toolbar_back_button.visibility = View.GONE
            }
        }

        fun createToolbarWithBackButton(
            view: AppCompatActivity,
            color: ToolbarColor,
            parent: Class<*>
        ) {
            createToolbar(view, color, true)

            view.the_toolbar_back_button.setOnClickListener {
                view.startActivity(
                    Intent(
                        view.applicationContext,
                        parent
                    ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
        }
    }

}