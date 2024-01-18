package hr.algebra.cryptotracker.repository

import hr.algebra.cryptotracker.enums.CustomResponse
import hr.algebra.cryptotracker.model.User

interface UserRepository {
    suspend fun registerUser(newUser: User): CustomResponse
    suspend fun loginUser(user: User): CustomResponse
}