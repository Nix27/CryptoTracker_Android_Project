package hr.algebra.cryptotracker.model

data class Comment(
    var id: String? = null,
    val currencyId: String? = null,
    val text: String? = null,
    val usernameOfUser: String? = null
)
