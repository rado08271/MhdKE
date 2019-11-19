package sk.rafig.mhdke.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import sk.rafig.mhdke.api.Cache
import sk.rafig.mhdke.util.ContextTags

class LegalViewModel(private val application: Application): ViewModel() {

    fun seenLegal(){
        Cache.addValueToCache(ContextTags.USER_SEEN_LEGAL, true, application)
    }
}