package hr.algebra.cryptotracker.repository

import hr.algebra.cryptotracker.api.CryptoFetcher
import hr.algebra.cryptotracker.api.MAIN_CURRENCY
import hr.algebra.cryptotracker.model.Currency
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CurrenciesRepositoryImpl : CurrenciesRepository {
    private val refreshInterval = 61000L

    override fun getLatestComments(): Flow<List<Currency>> {
        return flow {
            while(true) {
                val currenciesFromApi = CryptoFetcher().fetchCryptoCurrencies(MAIN_CURRENCY, 1)
                emit(currenciesFromApi)
                delay(refreshInterval)
            }
        }
    }
}