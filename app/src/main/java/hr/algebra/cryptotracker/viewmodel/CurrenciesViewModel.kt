package hr.algebra.cryptotracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.algebra.cryptotracker.api.CryptoFetcher
import hr.algebra.cryptotracker.model.Currency
import hr.algebra.cryptotracker.repository.CurrenciesRepository
import hr.algebra.cryptotracker.repository.CurrenciesRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrenciesViewModel : ViewModel() {
    private val currenciesRepository: CurrenciesRepository = CurrenciesRepositoryImpl()
    private val _currencies = MutableLiveData<List<Currency>>()
    val currencies: LiveData<List<Currency>> get() = _currencies

    init {
        viewModelScope.launch {
            currenciesRepository.getLatestComments().collect { latestCurrencies ->
                println("fetched latestCurrencies")
                withContext(Dispatchers.Main) {
                    _currencies.value = latestCurrencies
                    println("value changed")
                }
            }
        }
    }
}