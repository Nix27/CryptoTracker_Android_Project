package hr.algebra.cryptotracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.cryptotracker.framework.startActivity

class CryptoReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.startActivity<HostActivity>()
    }
}