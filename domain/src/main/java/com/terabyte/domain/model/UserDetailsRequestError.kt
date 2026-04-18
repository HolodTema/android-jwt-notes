package com.terabyte.domain.model

sealed class UserDetailsRequestError : Throwable() {

    class UnknownError : UserDetailsRequestError()

    class TokenExpiredError : UserDetailsRequestError()
}