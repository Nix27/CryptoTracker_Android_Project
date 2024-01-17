package hr.algebra.cryptotracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.algebra.cryptotracker.enums.UserResponse
import hr.algebra.cryptotracker.factory.getRepository
import hr.algebra.cryptotracker.model.User
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val userRepository = getRepository()
    private val _response = MutableLiveData<UserResponse>()
    val response: LiveData<UserResponse> get() = _response

    fun registerUser(username: String, password: String) {
        viewModelScope.launch {
            _response.value = userRepository.registerUser(User(username = username, password = password))
        }
    }
    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            _response.value = userRepository.loginUser(User(username = username, password = password))
        }
    }
}