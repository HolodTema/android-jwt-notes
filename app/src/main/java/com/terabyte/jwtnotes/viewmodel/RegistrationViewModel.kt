package com.terabyte.jwtnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terabyte.domain.model.RegisterCredentialsModel
import com.terabyte.domain.model.RegistrationError
import com.terabyte.domain.usecase.RegisterUseCase
import com.terabyte.domain.usecase.ValidateUsernameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val validateUsernameUseCase: ValidateUsernameUseCase
) : ViewModel() {

    private val _stateFlowRegisterScreenState = MutableStateFlow<RegistrationScreenState>(
        RegistrationScreenState()
    )
    val stateFlowRegisterScreenState = _stateFlowRegisterScreenState.asStateFlow()

    fun updatePasswordVisibility() {
        _stateFlowRegisterScreenState.update {
            it.copy(
                isPasswordVisible = !(it.isPasswordVisible)
            )
        }
    }

    fun updateUsername(username: String) {
        _stateFlowRegisterScreenState.update {
            it.copy(
                username = username
            )
        }
    }

    fun updateEmail(email: String) {
        _stateFlowRegisterScreenState.update {
            it.copy(
                email = email
            )
        }
    }

    fun updatePassword(password: String) {
        _stateFlowRegisterScreenState.update {
            it.copy(
                password = password
            )
        }
    }

    fun register() {
        val isUsernameValid = validateUsernameUseCase(stateFlowRegisterScreenState.value.username)
        if (!isUsernameValid) {
            _stateFlowRegisterScreenState.update {
                it.copy(
                    isErrorUsernameValidation = true
                )
            }
            return
        }

        _stateFlowRegisterScreenState.update {
            it.copy(
                isLoading = true,
                isErrorUnableToRegister = false,
                isErrorUsernameBusy = false,
                isErrorUsernameValidation = false
            )
        }

        val registerCredentialsModel = RegisterCredentialsModel(
            username = stateFlowRegisterScreenState.value.username.trim(),
            email = stateFlowRegisterScreenState.value.email.trim(),
            password = stateFlowRegisterScreenState.value.password.trim(),
        )
        viewModelScope.launch(Dispatchers.IO) {
            val registrationError = registerUseCase(registerCredentialsModel)
            withContext(Dispatchers.Main) {
                if (registrationError == null) {
                    //successful registration
                    _stateFlowRegisterScreenState.update {
                        it.copy(
                            isLoading = false,
                            registrationSuccess = true
                        )
                    }
                }
                else {
                    //some error happened
                    when (registrationError) {
                        is RegistrationError.UsernameBusy -> {
                            _stateFlowRegisterScreenState.update {
                                it.copy(
                                    isLoading = false,
                                    isErrorUnableToRegister = false,
                                    isErrorUsernameBusy = true,
                                )
                            }
                        }
                        is RegistrationError.UnknownError -> {
                            _stateFlowRegisterScreenState.update {
                                it.copy(
                                    isLoading = false,
                                    isErrorUnableToRegister = true,
                                    isErrorUsernameBusy = false,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

data class RegistrationScreenState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val registrationSuccess: Boolean = false,
    val isErrorUsernameBusy: Boolean = false,
    val isErrorUnableToRegister: Boolean = false,
    val isErrorUsernameValidation: Boolean = false,
    val isLoading: Boolean = false
)