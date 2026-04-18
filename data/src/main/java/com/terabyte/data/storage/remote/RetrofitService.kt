package com.terabyte.data.storage.remote

import com.terabyte.data.storage.remote.model.LoginRequestJson
import com.terabyte.data.storage.remote.model.LoginResponseJson
import com.terabyte.data.storage.remote.model.NoteJson
import com.terabyte.data.storage.remote.model.RegisterRequestJson
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {

    @POST("api/auth/login")
    suspend fun login(@Body loginRequestJson: LoginRequestJson): Response<LoginResponseJson>

    @POST("api/auth/register")
    suspend fun register(@Body registerRequestJson: RegisterRequestJson): Response<LoginResponseJson>

    @GET("api/notes")
    suspend fun getAllNotes(): Response<List<NoteJson>>

}