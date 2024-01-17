package hr.algebra.cryptotracker.model

data class User(
    var id: String? = null,
    val username: String,
    val password: String
)
