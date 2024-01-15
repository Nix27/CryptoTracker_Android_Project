package hr.algebra.cryptotracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.algebra.cryptotracker.api.CryptoFetcher
import hr.algebra.cryptotracker.model.Currency
import hr.algebra.cryptotracker.model.CurrencyPrice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyDetailsViewModel : ViewModel() {
    private val _currencyDetails = MutableLiveData<Currency>()
    private val _currencyPrices = MutableLiveData<List<CurrencyPrice>>()
    val currencyDetails: LiveData<Currency> get() = _currencyDetails
    val currencyPrices: LiveData<List<CurrencyPrice>> get() = _currencyPrices

    fun getCurrencyDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currencyDetailsFromApi = CryptoFetcher().fetchCryptoCurrencyDetails(id, "usd")
            withContext(Dispatchers.Main) {
                _currencyDetails.value = currencyDetailsFromApi
            }
        }
    }

    fun getCurrencyPrices(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currencyPricesFromApi = CryptoFetcher().fetchCurrencyPriceData(id, "usd", 2)
            withContext(Dispatchers.Main) {
                _currencyPrices.value = currencyPricesFromApi
            }
        }
    }
}