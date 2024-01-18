package hr.algebra.cryptotracker.repository

import hr.algebra.cryptotracker.model.Currency
import kotlinx.coroutines.flow.Flow

interface CurrenciesRepository {
    fun getLatestComments(): Flow<List<Currency>>
}