package com.terabyte.data.repository

import com.terabyte.data.storage.remote.NetworkStorage
import com.terabyte.data.storage.remote.model.UserDetailsJson
import com.terabyte.domain.model.UserDetailsModel
import com.terabyte.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val networkStorage: NetworkStorage
) : UserRepository {

    override suspend fun getUserDetails(): Result<UserDetailsModel> {
        val result = networkStorage.getUserDetails()
        return result.map {
            mapToUserDetailsModel(it)
        }
    }

    private fun mapToUserDetailsModel(userDetailsJson: UserDetailsJson): UserDetailsModel {
        return UserDetailsModel(
            username = userDetailsJson.username,
            email = userDetailsJson.email
        )
    }

}
