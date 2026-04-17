package com.terabyte.jwtnotes.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

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
}

data class LoginScreenState(
    val username: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val loginSuccess: Boolean = false,
    val textError: String? = null,
    val isLoading: Boolean = false
)