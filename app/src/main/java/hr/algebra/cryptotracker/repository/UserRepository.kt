package hr.algebra.cryptotracker.repository

interface UserRepository {
    fun registerUser(username: String, password: String): String
    fun loginUser(username: String, password: String): String
}