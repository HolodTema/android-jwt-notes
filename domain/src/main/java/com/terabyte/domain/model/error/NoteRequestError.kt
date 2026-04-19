package com.terabyte.domain.model.error

sealed class NoteRequestError : Throwable() {

    class UnknownError : NoteRequestError()

    class TokenExpiredError : NoteRequestError()
}