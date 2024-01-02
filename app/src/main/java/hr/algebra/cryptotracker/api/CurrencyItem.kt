package hr.algebra.cryptotracker.api

import com.google.gson.annotations.SerializedName

data class CurrencyItem(
    @SerializedName("id") val id : String,
    @SerializedName("symbol") val symbol : String,
    @SerializedName("name") val name : String,
    @SerializedName("image") val image : String,
    @SerializedName("current_price") val current_price : Double,
    @SerializedName("market_cap") val market_cap : Long,
    @SerializedName("market_cap_rank") val market_cap_rank : Int,
    @SerializedName("fully_diluted_valuation") val fully_diluted_valuation : Long,
    @SerializedName("total_volume") val total_volume : Double,
    @SerializedName("high_24h") val high_24h : Double,
    @SerializedName("low_24h") val low_24h : Double,
    @SerializedName("price_change_24h") val price_change_24h : Double,
    @SerializedName("price_change_percentage_24h") val price_change_percentage_24h : Double,
    @SerializedName("market_cap_change_24h") val market_cap_change_24h : Double,
    @SerializedName("market_cap_change_percentage_24h") val market_cap_change_percentage_24h : Double,
    @SerializedName("circulating_supply") val circulating_supply : Double,
    @SerializedName("total_supply") val total_supply : Double,
    @SerializedName("max_supply") val max_supply : Double,
    @SerializedName("ath") val ath : Double,
    @SerializedName("ath_change_percentage") val ath_change_percentage : Double,
    @SerializedName("ath_date") val ath_date : String,
    @SerializedName("atl") val atl : Double,
    @SerializedName("atl_change_percentage") val atl_change_percentage : Double,
    @SerializedName("atl_date") val atl_date : String,
    @SerializedName("last_updated") val last_updated : String
)