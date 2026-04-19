package com.terabyte.domain.repository

import com.terabyte.domain.model.UserDetailsModel

interface UserRepository {

    suspend fun getUserDetails(): Result<UserDetailsModel>

}
