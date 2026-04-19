package com.terabyte.domain.model.error

sealed class UserDetailsRequestError : Throwable() {

    class UnknownError : UserDetailsRequestError()

    class TokenExpiredError : UserDetailsRequestError()
}