package sk.rafig.mhdke.ui

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.DialogCompat
import com.google.android.libraries.places.api.Places
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_allow_location.*
import kotlinx.android.synthetic.main.dialog.view.*
import sk.rafig.mhdke.R
import sk.rafig.mhdke.api.local.Cache
import sk.rafig.mhdke.util.ContextTags
import sk.rafig.mhdke.util.Locator

class AllowLocationActivity : AppCompatActivity() {
    private val GPS_COARSE_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
    private val GPS_FINE_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
    private val GPS_BACKGROUND_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allow_location)

        val checkCoarse = checkCallingOrSelfPermission(GPS_COARSE_PERMISSION)
        val checkFine = checkCallingOrSelfPermission(GPS_FINE_PERMISSION)
        val checkBackground = checkCallingOrSelfPermission(GPS_BACKGROUND_PERMISSION)

        if (checkCoarse == PackageManager.PERMISSION_GRANTED &&
            checkFine == PackageManager.PERMISSION_GRANTED &&
            checkBackground == PackageManager.PERMISSION_GRANTED &&
            Locator.isEnabledGps(this)) {


            val intent = Intent(applicationContext, TicketActivity::class.java)
            intent.putExtra(ContextTags.GPS_ALLOWED, true)
            startActivity(intent)
        }

        id_allow_location_agree.setOnClickListener{
            if (Locator.isEnabledGps(this)) {
                Dexter.withActivity(this)
                    .withPermissions(GPS_COARSE_PERMISSION, GPS_FINE_PERMISSION, GPS_BACKGROUND_PERMISSION)
                    .withListener(object : MultiplePermissionsListener {
                        override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

                            val intent = Intent(applicationContext, TicketActivity::class.java)
                            intent.putExtra(ContextTags.GPS_ALLOWED, report!!.areAllPermissionsGranted())
                            startActivity(intent)
                        }

                        override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
                            token!!.continuePermissionRequest()
                        }

                    })
                    .check()
            } else {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.string_allow_location_activity_turn_gps_title))
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.string_allow_location_activity_turn_gps_positive),
                        DialogInterface.OnClickListener { dialog, id ->
                            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                        })
                    .setNegativeButton(getString(R.string.string_allow_location_activity_turn_gps_negative),
                        DialogInterface.OnClickListener { dialog, id ->
                            dialog.cancel()
                            dialog.dismiss()

                            val intent = Intent(applicationContext, TicketActivity::class.java)
                            intent.putExtra(ContextTags.GPS_ALLOWED, false)
                            startActivity(intent)
                        })
                    .create()
                    .show()
            }
        }
    }
}
