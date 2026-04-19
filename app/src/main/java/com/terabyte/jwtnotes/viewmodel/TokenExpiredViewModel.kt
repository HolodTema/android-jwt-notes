package com.terabyte.jwtnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terabyte.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TokenExpiredViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

}