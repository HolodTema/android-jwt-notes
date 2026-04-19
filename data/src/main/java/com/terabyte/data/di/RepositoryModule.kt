package com.terabyte.data.di

import com.terabyte.data.repository.AuthRepositoryImpl
import com.terabyte.data.repository.NoteRepositoryImpl
import com.terabyte.data.repository.TokenRepositoryImpl
import com.terabyte.data.repository.UserRepositoryImpl
import com.terabyte.domain.repository.AuthRepository
import com.terabyte.domain.repository.NoteRepository
import com.terabyte.domain.repository.TokenRepository
import com.terabyte.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindTokenRepository(tokenRepository: TokenRepositoryImpl): TokenRepository

    @Binds
    @Singleton
    abstract fun bindNoteRepository(noteRepository: NoteRepositoryImpl): NoteRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository

}