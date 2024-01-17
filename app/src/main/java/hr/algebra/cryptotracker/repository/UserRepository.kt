package hr.algebra.cryptotracker.repository

import hr.algebra.cryptotracker.enums.UserResponse
import hr.algebra.cryptotracker.model.User

interface UserRepository {
    suspend fun registerUser(newUser: User): UserResponse
    suspend fun loginUser(user: User): UserResponse
}