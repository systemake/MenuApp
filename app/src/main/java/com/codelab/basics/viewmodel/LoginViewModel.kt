package com.codelab.basics.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.codelab.basics.data.TypeUser
import kotlinx.coroutines.launch


class LoginViewModel: BaseViewModel() {
    var username by mutableStateOf("run")
        private set

    var password by mutableStateOf("run")
        private set

    var loginSuccess by mutableStateOf<Boolean?>(null)
        private set

    var typeUser by mutableStateOf(TypeUser.WAITER.value)

    fun onUsernameChange(newValue: String) {
        username = newValue
    }

    fun onPasswordChange(newValue: String) {
        password = newValue
    }

    fun login() {
        viewModelScope.launch {
            isLoading = true
            try {
                if( username == "wt" && password == "wt"){
                    typeUser = TypeUser.WAITER.value
                    loginSuccess = true

                }else if( username == "run" && password == "run"){
                    typeUser = TypeUser.RUNNER.value
                    loginSuccess = true
                }

            } finally {
                isLoading = false
            }
        }
    }

    fun resetLoginState() {
        loginSuccess = null
    }
}