package hr.algebra.cryptotracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.algebra.cryptotracker.api.CryptoFetcher
import hr.algebra.cryptotracker.enums.CustomResponse
import hr.algebra.cryptotracker.model.Comment
import hr.algebra.cryptotracker.model.Currency
import hr.algebra.cryptotracker.model.CurrencyPrice
import hr.algebra.cryptotracker.repository.CommentRepository
import hr.algebra.cryptotracker.repository.CommentRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyDetailsViewModel : ViewModel() {
    private val commentRepository: CommentRepository = CommentRepositoryImpl()

    private val _currencyDetails = MutableLiveData<Currency>()
    private val _currencyPrices = MutableLiveData<List<CurrencyPrice>>()
    private val _comments = MutableLiveData<MutableList<Comment>>()
    val currencyDetails: LiveData<Currency> get() = _currencyDetails
    val currencyPrices: LiveData<List<CurrencyPrice>> get() = _currencyPrices
    val comments: LiveData<MutableList<Comment>> get() = _comments

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

    fun getComments(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _comments.value = commentRepository.getAllCommentsFor(id)
        }
    }

    fun addNewComment(text: String, usernameOfUser: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newComment = Comment(text = text, usernameOfUser = usernameOfUser)
            val response = commentRepository.addComment(newComment)
            withContext(Dispatchers.Main) {
                if(response == CustomResponse.SUCCESS) {
                    _comments.value!!.add(newComment)
                }
            }
        }
    }
}