package com.terabyte.data.storage.remote

import com.terabyte.data.storage.remote.model.LoginRequestJson
import com.terabyte.data.storage.remote.model.LoginResponseJson
import com.terabyte.data.storage.remote.model.NoteJson
import com.terabyte.data.storage.remote.model.NoteRequestJson
import com.terabyte.data.storage.remote.model.RegisterRequestJson
import com.terabyte.data.storage.remote.model.UserDetailsJson
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface RetrofitService {

    @POST("api/auth/login")
    suspend fun login(@Body loginRequestJson: LoginRequestJson): Response<LoginResponseJson>

    @POST("api/auth/register")
    suspend fun register(@Body registerRequestJson: RegisterRequestJson): Response<LoginResponseJson>

    @GET("api/notes")
    suspend fun getAllNotes(): Response<List<NoteJson>>

    @GET("api/user")
    suspend fun getUserDetails(): Response<UserDetailsJson>


    // actually we do not handle body of this POST request. That is why we use ResponseBody type
    @POST("api/notes")
    suspend fun createNote(@Body noteRequestJson: NoteRequestJson): Response<ResponseBody>


    // actually we do not handle body of this PUT request. That is why we use ResponseBody type
    @PUT("api/notes/{id}")
    suspend fun updateNote(
        @Query("id") noteId: Int,
        @Body noteRequestJson: NoteRequestJson
    ): Response<ResponseBody>

    @DELETE("api/notes/{id}")
    suspend fun deleteNote(
        @Query("id") noteId: Int,
    ): Response<ResponseBody>

    @GET("api/notes/{id}")
    suspend fun getNoteById(@Query("id") noteId: Int): Response<NoteJson>
}
