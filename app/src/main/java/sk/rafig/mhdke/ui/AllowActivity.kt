package sk.rafig.mhdke.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import kotlinx.android.synthetic.main.activity_allow.*
import sk.rafig.mhdke.R
import androidx.lifecycle.ViewModelProviders
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import sk.rafig.mhdke.viewmodel.AllowViewModel
import sk.rafig.mhdke.ui.toolbar.Toolbar
import sk.rafig.mhdke.ui.toolbar.ToolbarColor
import sk.rafig.mhdke.viewmodel.ViewModelFactory


class AllowActivity : AppCompatActivity() {

    private val SEND_PERMISSION = Manifest.permission.SEND_SMS
    private val RECEIVE_PERMISSION = Manifest.permission.RECEIVE_SMS
    private lateinit var viewModel: AllowViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allow)
        setActionBar(findViewById(R.id.id_activity_allow_toolbar))
        Toolbar.createToolbar(this, ToolbarColor.BLACK, false)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(application)).get(AllowViewModel::class.java)


        val checkVal = checkCallingOrSelfPermission(SEND_PERMISSION)

        if (checkVal == PackageManager.PERMISSION_GRANTED) {
            startActivity(Intent(applicationContext, TicketActivity::class.java))
        }

        //Dexter will take care of it!
        allow_agree.setOnClickListener{
            Dexter.withActivity(this)
                .withPermissions(SEND_PERMISSION, RECEIVE_PERMISSION )
                .withListener(object: MultiplePermissionsListener {
                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        token!!.continuePermissionRequest()
                    }

                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        if (report!!.areAllPermissionsGranted() ) {
                            startActivity(Intent(applicationContext, TicketActivity::class.java))
                        } else {
                            startActivity(Intent(applicationContext, OopsActivity::class.java))
                        }
                    }

//                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
//                    }
//                    override fun onPermissionRationaleShouldBeShown( permission: PermissionRequest?, token: PermissionToken?) {                    }
//                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
//                    }
                })
                .check()
        }

        allow_disagree.setOnClickListener {
            startActivity(Intent(applicationContext, OopsActivity::class.java))
        }
    }


}
