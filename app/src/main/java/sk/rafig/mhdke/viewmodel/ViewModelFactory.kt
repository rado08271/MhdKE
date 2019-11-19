package sk.rafig.mhdke.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import org.hotovo.mhdke.viewmodel.*
import java.lang.IllegalArgumentException

class ViewModelFactory(val application: Application): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) ->
                SplashViewModel(application) as T

            modelClass.isAssignableFrom(WelcomeViewModel::class.java) ->
                WelcomeViewModel(application) as T

            modelClass.isAssignableFrom(AllowViewModel::class.java) ->
                AllowViewModel(application) as T

            modelClass.isAssignableFrom(HistoryViewModel::class.java) ->
                HistoryViewModel(application) as T

            modelClass.isAssignableFrom(TicketPreviewViewModel::class.java) ->
                TicketPreviewViewModel(application) as T

            modelClass.isAssignableFrom(TicketViewModel::class.java) ->
                TicketViewModel(application) as T

            modelClass.isAssignableFrom(LegalViewModel::class.java) ->
                LegalViewModel(application) as T

            else -> throw IllegalArgumentException("Unknown Viewmodel class!") as Throwable
        }
    }
}