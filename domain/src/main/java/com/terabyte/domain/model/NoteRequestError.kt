package com.terabyte.domain.model

sealed class NoteRequestError : Throwable() {

    class UnknownError : NoteRequestError()

    class TokenExpiredError : NoteRequestError()
}