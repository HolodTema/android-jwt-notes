package com.terabyte.domain.usecase

import com.terabyte.domain.model.UserDetailsModel
import com.terabyte.domain.repository.UserRepository
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Result<UserDetailsModel> {
        return userRepository.getUserDetails()
    }

}

