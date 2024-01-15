package hr.algebra.cryptotracker.api

import com.google.gson.annotations.SerializedName

data class CurrencyChartData(
    @SerializedName("prices") val prices : List<List<Double>>,
    @SerializedName("market_caps") val market_caps : List<List<Double>>,
    @SerializedName("total_volumes") val total_volumes : List<List<Double>>
)