package com.terabyte.domain.model

sealed class RegistrationError : Throwable() {
    class UsernameBusy : RegistrationError()
    class UnknownError : RegistrationError()
}