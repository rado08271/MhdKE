package sk.rafig.mhdke.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_allow.*
import sk.rafig.mhdke.R
import androidx.core.content.PermissionChecker.checkCallingOrSelfPermission
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.hotovo.mhdke.viewmodel.AllowViewModel
import sk.rafig.mhdke.model.User
import sk.rafig.mhdke.viewmodel.ViewModelFactory


class AllowActivity : AppCompatActivity() {

    private val SEND_PERMISSION = Manifest.permission.SEND_SMS
    private lateinit var viewModel: AllowViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allow)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(application)).get(AllowViewModel::class.java)


        val checkVal = checkCallingOrSelfPermission(SEND_PERMISSION)

        if (checkVal == PackageManager.PERMISSION_GRANTED) {
            startActivity(Intent(applicationContext, TicketActivity::class.java))
        }

        //Dexter will take care of it!
        allow_agree.setOnClickListener{
            Dexter.withActivity(this)
                .withPermission(SEND_PERMISSION)
                .withListener(object: PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        startActivity(Intent(applicationContext, TicketActivity::class.java))
                    }
                    override fun onPermissionRationaleShouldBeShown( permission: PermissionRequest?, token: PermissionToken?) {                    }
                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        startActivity(Intent(applicationContext, OopsActivity::class.java))
                    }
                })
                .check()
        }

        allow_disagree.setOnClickListener {
            startActivity(Intent(applicationContext, OopsActivity::class.java))
        }
    }


}
