package hr.algebra.cryptotracker.api

import android.content.Context
import android.util.Log
import hr.algebra.cryptotracker.CryptoReceiver
import hr.algebra.cryptotracker.framework.sendBroadcast
import hr.algebra.cryptotracker.model.Currency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CryptoFetcher(private val context: Context) {
    private val cryptoApi: CryptoApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        cryptoApi = retrofit.create(CryptoApi::class.java)
    }

    suspend fun fetchCryptoCurrencies(count: Int): List<Currency> {
        return suspendCancellableCoroutine {
            var request = cryptoApi.fetchCurrencies(vsCurrency = "usd", perPage = count, page = 1)

            request.enqueue(object : Callback<List<CurrencyItem>> {
                override fun onResponse(
                    call: Call<List<CurrencyItem>>,
                    response: Response<List<CurrencyItem>>
                ) {
                    response.body()?.let { getCurrencies(it) }
                }

                override fun onFailure(call: Call<List<CurrencyItem>>, t: Throwable) {
                    Log.e(javaClass.name, t.toString(), t)
                }
            })
        }
    }

    private fun getCurrencies(currencyItems: List<CurrencyItem>): List<Currency> {
        val currencies = mutableListOf<Currency>()

        currencyItems.forEach {
            currencies.add(Currency(
                it.symbol,
                it.image,
                it.name,
                it.current_price,
                it.market_cap,
                it.market_cap_rank,
                it.total_volume,
                it.ath,
                it.high_24h,
                it.low_24h,
                it.circulating_supply,
                it.total_supply,
                it.max_supply
            ))
        }

        return currencies
    }
}