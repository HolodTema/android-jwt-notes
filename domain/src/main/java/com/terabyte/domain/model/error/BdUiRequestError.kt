package com.terabyte.domain.model.error

sealed class BdUiRequestError : Throwable() {

    class UnknownError : BdUiRequestError()

    class TokenExpiredError : BdUiRequestError()
}