package com.terabyte.domain.repository

import com.terabyte.domain.model.user.UserDetailsModel

interface UserRepository {

    suspend fun getUserDetails(): Result<UserDetailsModel>

}
