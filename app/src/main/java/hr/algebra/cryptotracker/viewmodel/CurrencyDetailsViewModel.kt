package hr.algebra.cryptotracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.algebra.cryptotracker.api.CryptoFetcher
import hr.algebra.cryptotracker.api.MAIN_CURRENCY
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

    private val _currencyPrices = MutableLiveData<List<CurrencyPrice>>()
    private val _comments = MutableLiveData<List<Comment>>()
    val currencyPrices: LiveData<List<CurrencyPrice>> get() = _currencyPrices
    val comments: LiveData<List<Comment>> get() = _comments

    fun getCurrencyPrices(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currencyPricesFromApi = CryptoFetcher().fetchCurrencyPriceData(id, MAIN_CURRENCY, 2)
            withContext(Dispatchers.Main) {
                _currencyPrices.value = currencyPricesFromApi
            }
        }
    }

    fun getComments(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val commentsFromDb = commentRepository.getAllCommentsFor(id)
            withContext(Dispatchers.Main) {
                _comments.value = commentsFromDb
            }
        }
    }

    fun addNewComment(currencyId: String, text: String, usernameOfUser: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newComment = Comment(currencyId = currencyId, text = text, usernameOfUser = usernameOfUser)
            val response = commentRepository.addComment(newComment)
            withContext(Dispatchers.Main) {
                if(response == CustomResponse.SUCCESS) {
                    val mutableComments = _comments.value!!.toMutableList()
                    mutableComments.add(newComment)
                    _comments.value = mutableComments
                }
            }
        }
    }
}