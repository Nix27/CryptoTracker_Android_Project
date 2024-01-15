package hr.algebra.cryptotracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.algebra.cryptotracker.api.CryptoFetcher
import hr.algebra.cryptotracker.model.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrenciesViewModel : ViewModel() {
    private val _currencies = MutableLiveData<List<Currency>>()
    val currencies: LiveData<List<Currency>> get() = _currencies

    init {
        viewModelScope.launch(Dispatchers.IO) {
            var currenciesFromApi = CryptoFetcher().fetchCryptoCurrencies("usd", 1)
            withContext(Dispatchers.Main) {
                _currencies.value = currenciesFromApi
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        // stop refreshing
    }

}