package sk.rafig.mhdke.util

import android.content.Context
import android.icu.text.Transliterator
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import androidx.core.content.ContextCompat.getSystemService
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import java.util.*


object Locator {

    fun getLocation(context: Context): Task<Location> {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        val result = fusedLocationClient.lastLocation

        return result
    }

    fun isEnabledGps(context: Context): Boolean{
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            return true
        }

        return false
    }

    fun getCityFromLatLng(context: Context, latLng: LatLng): String? {
        if (latLng.latitude == 0.0 && latLng.longitude == 0.0) {
            return null
        }
        val geocoder: Geocoder = Geocoder(context, Locale.ROOT)
        val address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

        return address[0].locality
    }

    fun setNumberBasedOnCity(city: String): Boolean {
        return SmsSpecs.setNewLocation(city)
    }
}