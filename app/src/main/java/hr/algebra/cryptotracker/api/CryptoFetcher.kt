package hr.algebra.cryptotracker.api

import android.util.Log
import hr.algebra.cryptotracker.formatter.formatTimestampToTime
import hr.algebra.cryptotracker.model.Currency
import hr.algebra.cryptotracker.model.CurrencyPrice
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resume

class CryptoFetcher {
    private val cryptoApi: CryptoApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        cryptoApi = retrofit.create(CryptoApi::class.java)
    }

    suspend fun fetchCryptoCurrencies(vsCurrency: String, page: Int): List<Currency> {
        return suspendCancellableCoroutine  { continuation ->
            val request = cryptoApi.fetchCurrencies(vsCurrency = vsCurrency, page = page)

            request.enqueue(object : Callback<List<CurrencyItem>> {
                override fun onResponse(
                    call: Call<List<CurrencyItem>>,
                    response: Response<List<CurrencyItem>>
                ) {
                    response.body()?.let {
                        continuation.resume(getCurrencies(it))
                    }
                }

                override fun onFailure(call: Call<List<CurrencyItem>>, t: Throwable) {
                    Log.e(javaClass.name, t.toString(), t)
                }
            })

            continuation.invokeOnCancellation {
                request.cancel()
            }
        }
    }

    suspend fun fetchCurrencyPriceData(id: String, vsCurrency: String, days: Int): List<CurrencyPrice> {
        return suspendCancellableCoroutine { continuation ->
            val request = cryptoApi.fetchCurrencyChartData(id, vsCurrency, days.toString())

            request.enqueue(object : Callback<CurrencyChartData> {
                override fun onResponse(
                    call: Call<CurrencyChartData>,
                    response: Response<CurrencyChartData>
                ) {
                    response.body()?.let {
                        continuation.resume(getCurrencyChartDataPrices(it))
                    }
                }

                override fun onFailure(call: Call<CurrencyChartData>, t: Throwable) {
                    Log.e(javaClass.name, t.toString(), t)
                }
            })

            continuation.invokeOnCancellation {
                request.cancel()
            }
        }
    }

    private fun getCurrencies(currencyItems: List<CurrencyItem>): List<Currency> {
        val currencies = mutableListOf<Currency>()

        currencyItems.forEach {
            currencies.add(Currency(
                it.id,
                it.symbol,
                it.name,
                it.image,
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

    private fun getCurrencyChartDataPrices(currencyChartData: CurrencyChartData) : List<CurrencyPrice> {
        val currencyPrices = mutableListOf<CurrencyPrice>()

        currencyChartData.prices.takeLast(24).forEach {
            currencyPrices.add(CurrencyPrice(
                formatTimestampToTime(it[0].toLong()),
                it[1]
            ))
        }

        return currencyPrices.sortedBy { it.time }
    }
}