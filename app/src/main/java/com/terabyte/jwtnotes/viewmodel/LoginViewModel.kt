package com.terabyte.jwtnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terabyte.domain.model.LoginCredentialsModel
import com.terabyte.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
        val loginCredentialsModel = LoginCredentialsModel(
            username = stateFlowLoginScreenState.value.username.trim(),
            password = stateFlowLoginScreenState.value.password.trim(),
        )
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase(loginCredentialsModel)
        }
    }
}

data class LoginScreenState(
    val username: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val loginSuccess: Boolean = false,
    val textError: String? = null,
    val isLoading: Boolean = false
)