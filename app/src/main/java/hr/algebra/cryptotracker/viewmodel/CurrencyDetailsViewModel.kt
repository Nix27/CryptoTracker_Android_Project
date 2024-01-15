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

class CurrencyDetailsViewModel : ViewModel() {
    private val _currencyDetails = MutableLiveData<Currency>()
    val currencyDetails: LiveData<Currency> get() = _currencyDetails

    fun getCurrencyDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currencyDetailsFromApi = CryptoFetcher().fetchCryptoCurrencyDetails(id, "usd")
            withContext(Dispatchers.Main) {
                _currencyDetails.value = currencyDetailsFromApi
            }
        }
    }
}