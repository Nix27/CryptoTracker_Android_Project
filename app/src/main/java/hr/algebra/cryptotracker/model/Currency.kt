package hr.algebra.cryptotracker.model

data class Currency(
    val _id : Long?,
    val symbol : String,
    val name : String,
    val imagePath : String,
    val current_price : Double,
    val market_cap : Long,
    val market_cap_rank : Int,
    val total_volume : Double,
    val ath : Double,
    val high_24h : Double,
    val low_24h : Double,
    val circulating_supply : Double,
    val total_supply : Double,
    val max_supply : Double?,
)
