package com.terabyte.data.di

import com.terabyte.data.storage.remote.NetworkStorage
import com.terabyte.data.storage.remote.NetworkStorageImpl
import com.terabyte.data.storage.remote.RetrofitService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun bindNetworkStorage(networkStorage: NetworkStorageImpl): NetworkStorage

    companion object {

        @JvmStatic
        @Provides
        @Singleton
        fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                //BODY = log url, http method, response code, handle-time,
                //headers + request/response body
                //
                //logs will be in AndroidStudio Logcat.
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        @JvmStatic
        @Provides
        @Singleton
        fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()
        }

        @JvmStatic
        @Provides
        @Singleton
        fun provideGsonConverterFactory(): GsonConverterFactory {
            return GsonConverterFactory.create()
        }

        @JvmStatic
        @Provides
        @Singleton
        fun provideRetrofit(client: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://185.154.52.200/api")
                .client(client)
                .addConverterFactory(gsonConverterFactory)
                .build()
        }

        @JvmStatic
        @Provides
        @Singleton
        fun provideRetrofitService(retrofit: Retrofit): RetrofitService {
            return retrofit.create(RetrofitService::class.java)
        }
    }
}