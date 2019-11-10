package sk.rafig.mhdke.api

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import java.util.prefs.Preferences


class Cache(val context: Context) {
    private var sharedpreferences: SharedPreferences = context.getSharedPreferences("MHD", Context.MODE_PRIVATE)

    private var user = "userId"
    private var seenOnboarding = "onboarding"
    private var timestamp = "time"
    //TODO maybe add whether user exists...

    fun getUserId(): String{
        return sharedpreferences.getString(user, null)!!
    }

    fun createUserId(id: String){
        sharedpreferences.edit().putString(user, id).apply()
    }

    fun seenOnBoarding(): Boolean{
        return sharedpreferences.getBoolean(seenOnboarding, true)
    }

    fun onboardingDone(){
        sharedpreferences.edit().putBoolean(user, true).apply()
    }

    fun addTimeStamp(time: String){
        sharedpreferences.edit().putString(timestamp, time).apply()
    }

    fun getTimeStamp(): String {
        return sharedpreferences.getString(timestamp, "NULL")!!
    }

}