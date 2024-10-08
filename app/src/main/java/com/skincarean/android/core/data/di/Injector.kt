package com.skincarean.android.core.data.di

import com.skincarean.android.core.data.repository.UserRepository
import com.skincarean.android.core.data.source.remote.RemoteDataSource
import com.skincarean.android.core.data.source.remote.network.ApiService
import com.skincarean.android.ui.ViewModelFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Injector {
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .readTimeout(30, TimeUnit.SECONDS) // Set timeout untuk membaca data
            .connectTimeout(30, TimeUnit.SECONDS) // Set timeout untuk koneksi
            .writeTimeout(30, TimeUnit.SECONDS) // Set timeout untuk menulis data
            .build()
    }

    private fun provideApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.122.184:8080/")
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }


    private fun provideRemoteDataSource(): RemoteDataSource {
        return RemoteDataSource.getInstance(provideApiService())
    }

    private fun provideUserRepository(): UserRepository {
        return UserRepository.getInstance(provideRemoteDataSource())
    }

     fun provideViewModelFactory(): ViewModelFactory {
        return ViewModelFactory.getInstance(provideUserRepository())
    }
}
