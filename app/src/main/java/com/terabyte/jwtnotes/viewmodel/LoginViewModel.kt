package com.terabyte.jwtnotes.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _stateFlowLoginScreenState = MutableStateFlow<LoginScreenState>(LoginScreenState())
    val stateFlowLoginScreenState = _stateFlowLoginScreenState.asStateFlow()


}

data class LoginScreenState(
    val loginSuccess: Boolean = false,
    val textError: String? = null,
    val isLoading: Boolean = false
)