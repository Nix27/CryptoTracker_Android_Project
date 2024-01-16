package hr.algebra.cryptotracker.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_URL = "https://api.coingecko.com/api/v3/"
interface CryptoApi {
    @GET("coins/markets")
    fun fetchCurrencies(@Query("vs_currency") vsCurrency: String = "usd",
                        @Query("per_page") perPage: Int = 100,
                        @Query("page") page: Int = 1,
                        @Query("order") order: String = "market_cap_rank") : Call<List<CurrencyItem>>

    @GET("coins/markets")
    fun fetchCurrencyDetails(@Query("ids") ids: String,
                             @Query("vs_currency") vsCurrency: String = "usd") : Call<List<CurrencyItem>>

    @GET("coins/{id}/market_chart")
    fun fetchCurrencyChartData(@Path("id") id: String,
                                 @Query("vs_currency") vsCurrency: String = "usd",
                                 @Query("days") days: String = "2") : Call<CurrencyChartData>
}