package com.tuenti.compracolaborativa.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tuenti.compracolaborativa.R
import com.tuenti.compracolaborativa.data.LoginRepository
import com.tuenti.compracolaborativa.data.Result

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(username, password)

        if (result is Result.Success) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

    fun loginDataChanged(username: String, address: String, address_detail: String) {
        if (username.isNullOrEmpty() || address.isNullOrEmpty() || address_detail.isNullOrEmpty()) {
            return
        }

        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(nameError = R.string.invalid_name)
        } else if (!isAddressValid(address)) {
            _loginForm.value = LoginFormState(addressError = R.string.invalid_address)
        } else if (!isAddressDetailValid(address_detail)) {
            _loginForm.value = LoginFormState(addressDetailError = R.string.invalid_address_detail)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean = username.length > 1

    private fun isAddressValid(address: String): Boolean = address.length > 1
    private fun isAddressDetailValid(detail: String): Boolean = detail.length > 1
}
