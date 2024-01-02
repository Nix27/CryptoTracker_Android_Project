package hr.algebra.cryptotracker.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val API_URL = "https://api.coingecko.com/api/v3/"
interface CryptoApi {
    @GET("coins/markets")
    fun fetchCurrencies(@Query("vs_currency") vsCurrency: String = "usd",
                        @Query("per_page") perPage: Int = 100,
                        @Query("page") page: Int = 1) : Call<List<CurrencyItem>>
}