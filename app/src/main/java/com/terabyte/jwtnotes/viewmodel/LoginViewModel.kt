package com.terabyte.jwtnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terabyte.domain.model.auth.LoginCredentialsModel
import com.terabyte.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _stateFlowLoginScreenState = MutableStateFlow<LoginScreenState>(LoginScreenState())
    val stateFlowLoginScreenState = _stateFlowLoginScreenState.asStateFlow()

    fun updatePasswordVisibility() {
        _stateFlowLoginScreenState.update {
            it.copy(
                isPasswordVisible = !(it.isPasswordVisible)
            )
        }
    }

    fun updateUsername(username: String) {
        _stateFlowLoginScreenState.update {
            it.copy(
                username = username
            )
        }
    }

    fun updatePassword(password: String) {
        _stateFlowLoginScreenState.update {
            it.copy(
                password = password
            )
        }
    }

    fun login() {
        _stateFlowLoginScreenState.update {
            it.copy(
                isLoading = true,
                isLoginError = false
            )
        }

        val loginCredentialsModel = LoginCredentialsModel(
            username = stateFlowLoginScreenState.value.username.trim(),
            password = stateFlowLoginScreenState.value.password.trim(),
        )
        viewModelScope.launch(Dispatchers.IO) {
            val isLoginSuccess = loginUseCase(loginCredentialsModel)
            withContext(Dispatchers.Main) {
                if (isLoginSuccess) {
                    _stateFlowLoginScreenState.update {
                        it.copy(
                            isLoading = false,
                            loginSuccess = true
                        )
                    }
                }
                else {
                    _stateFlowLoginScreenState.update {
                        it.copy(
                            isLoading = false,
                            isLoginError = true
                        )
                    }
                }
            }
        }
    }
}

data class LoginScreenState(
    val username: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val loginSuccess: Boolean = false,
    val isLoginError: Boolean = false,
    val isLoading: Boolean = false
)