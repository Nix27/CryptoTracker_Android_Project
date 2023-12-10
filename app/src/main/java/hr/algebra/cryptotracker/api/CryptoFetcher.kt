package hr.algebra.cryptotracker.api

import android.content.Context
import hr.algebra.cryptotracker.CryptoReceiver
import hr.algebra.cryptotracker.framework.sendBroadcast

class CryptoFetcher(private val context: Context) {
    fun fetchCryptoCurrencies(count: Int){
        Thread.sleep(2000)

        context.sendBroadcast<CryptoReceiver>()
    }
}