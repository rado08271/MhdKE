package sk.rafig.mhdke.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import sk.rafig.mhdke.api.local.Cache
import sk.rafig.mhdke.util.ContextTags

class WelcomeViewModel(val application: Application): ViewModel() {
    fun welcomeSeen() {
        Cache.addValueToCache(ContextTags.USER_SEEN_WELCOME, true, application)
    }
}