package hr.algebra.cryptotracker.framework

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.getSystemService
import androidx.preference.PreferenceManager

fun View.applyAnimation(animationId: Int) = startAnimation(
    AnimationUtils.loadAnimation(context, animationId)
)

inline fun<reified T : Activity> Context.startActivity()
   = startActivity(Intent(this, T::class.java).apply {
       addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    })

inline fun<reified T : BroadcastReceiver> Context.sendBroadcast()
        = sendBroadcast(Intent(this, T::class.java))

fun Context.setStringPreference(key: String, value: String)
    = PreferenceManager.getDefaultSharedPreferences(this)
    .edit()
    .putString(key, value)
    .apply()

fun Context.getStringPreference(key:String)
    = PreferenceManager.getDefaultSharedPreferences(this)
    .getString(key, "")

fun callDelayed(delay: Long, work: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(
        work,
        delay
    )
}

fun Context.isOnline() : Boolean {
    val connectivityManager = getSystemService<ConnectivityManager>()

    connectivityManager?.activeNetwork?.let { network ->
        connectivityManager.getNetworkCapabilities(network)?.let { networkCapabilities ->
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        }
    }

    return false
}