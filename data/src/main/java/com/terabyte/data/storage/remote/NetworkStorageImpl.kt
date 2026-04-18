package com.terabyte.data.storage.remote

import com.terabyte.data.storage.remote.model.LoginRequestJson
import com.terabyte.data.storage.remote.model.LoginResponseJson
import com.terabyte.data.storage.remote.model.NoteJson
import com.terabyte.data.storage.remote.model.RegisterRequestJson
import com.terabyte.data.storage.remote.model.UserDetailsJson
import com.terabyte.domain.model.NoteRequestError
import com.terabyte.domain.model.RegistrationError
import com.terabyte.domain.model.UserDetailsRequestError
import okhttp3.internal.http2.ErrorCode
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkStorageImpl @Inject constructor(
    private val retrofitService: RetrofitService
) : NetworkStorage {

    override suspend fun login(loginRequestJson: LoginRequestJson): Result<LoginResponseJson> {
        val response = retrofitService.login(loginRequestJson)
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null) {
                Result.failure(Exception(response.code().toString()))
            } else {
                Result.success(body)
            }
        } else {
            Result.failure(Exception(response.code().toString()))
        }
    }

    override suspend fun register(registerRequestJson: RegisterRequestJson): Result<LoginResponseJson> {
        val response = retrofitService.register(registerRequestJson)
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null) {
                Result.failure(RegistrationError.UnknownError())
            } else {
                Result.success(body)
            }
        }
        else {
            if (response.code() == 409) {
                Result.failure(RegistrationError.UsernameBusy())
            }
            else {
                Result.failure(RegistrationError.UnknownError())
            }
        }
    }

    override suspend fun getAllNotes(): Result<List<NoteJson>> {
        val response = retrofitService.getAllNotes()
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null) {
                Result.failure(NoteRequestError.UnknownError())
            }
            else {
                Result.success(body)
            }
        }
        else {
            if (response.code() == 401) {
                Result.failure(NoteRequestError.TokenExpiredError())
            }
            else {
                Result.failure(NoteRequestError.UnknownError())
            }
        }
    }

    override suspend fun getUserDetails(): Result<UserDetailsJson> {
        val response = retrofitService.getUserDetails()
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null) {
                Result.failure(UserDetailsRequestError.UnknownError())
            }
            else {
                Result.success(body)
            }
        }
        else {
            if (response.code() == 401) {
                Result.failure(UserDetailsRequestError.TokenExpiredError())
            }
            else {
                Result.failure(UserDetailsRequestError.UnknownError())
            }
        }
    }
}