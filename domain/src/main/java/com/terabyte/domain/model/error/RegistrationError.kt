package com.terabyte.domain.model.error

sealed class RegistrationError : Throwable() {
    class UsernameBusy : RegistrationError()
    class UnknownError : RegistrationError()
}