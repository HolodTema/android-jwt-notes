package com.terabyte.data.storage.remote

import com.terabyte.data.storage.remote.model.LoginRequestJson
import com.terabyte.data.storage.remote.model.LoginResponseJson
import com.terabyte.data.storage.remote.model.RegisterRequestJson
import com.terabyte.domain.model.RegistrationError
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
}