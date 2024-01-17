package hr.algebra.cryptotracker.factory

import hr.algebra.cryptotracker.repository.UserRepository
import hr.algebra.cryptotracker.repository.UserRepositoryImpl

fun getRepository(): UserRepository {
    return UserRepositoryImpl()
}