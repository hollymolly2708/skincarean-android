package com.skincarean.android.core.data.di

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.skincarean.android.Utilities
import com.skincarean.android.core.data.LoginSharedPref
import com.skincarean.android.core.data.repository.BrandRepository
import com.skincarean.android.core.data.repository.CartRepository
import com.skincarean.android.core.data.repository.OrderRepository
import com.skincarean.android.core.data.repository.PaymentMethodRepository
import com.skincarean.android.core.data.repository.ProductRepository
import com.skincarean.android.core.data.repository.UserRepository
import com.skincarean.android.core.data.source.remote.BrandRemoteDataSource
import com.skincarean.android.core.data.source.remote.CartRemoteDataSource
import com.skincarean.android.core.data.source.remote.OrderRemoteDataSource
import com.skincarean.android.core.data.source.remote.PaymentMethodRemoteDataSource
import com.skincarean.android.core.data.source.remote.ProductRemoteDataSource
import com.skincarean.android.core.data.source.remote.UserRemoteDataSource
import com.skincarean.android.core.data.source.remote.network.ApiService
import com.skincarean.android.ui.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Injector {
    private lateinit var appContext: Context
    fun init(context: Context) {
        appContext = context.applicationContext
        LoginSharedPref.getToken(context)
    }

    private fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val token = LoginSharedPref.getToken(appContext)
            val newRequest = if (token != null) {
                chain.request().newBuilder()
                    .addHeader("X-API-TOKEN", token)
                    .build()
            } else {
                chain.request()
            }
            // Ini akan mengembalikan hasil chain.proceed(newRequest) secara implisit
            val response = chain.proceed(newRequest)

            if (response.code == 401) {
                if (!LoginSharedPref.getToken(appContext).isNullOrEmpty()) {
                    LoginSharedPref.clear(appContext)
                }
            }
            response
        }
    }


    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(provideInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .readTimeout(30, TimeUnit.SECONDS) // Set timeout untuk membaca data
            .connectTimeout(30, TimeUnit.SECONDS) // Set timeout untuk koneksi
            .writeTimeout(30, TimeUnit.SECONDS) // Set timeout untuk menulis data
            .build()
    }

    private fun provideApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.142.184:8080/")
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    private fun provideUserRemoteDataSource(): UserRemoteDataSource {
        return UserRemoteDataSource.getInstance(provideApiService())
    }

    private fun provideUserRepository(): UserRepository {
        return UserRepository.getInstance(provideUserRemoteDataSource())
    }

    private fun provideBrandRemoteDataSource(): BrandRemoteDataSource {
        return BrandRemoteDataSource.getInstance(provideApiService())
    }

    private fun provideBrandRepository(): BrandRepository {
        return BrandRepository.getInstance(provideBrandRemoteDataSource())
    }

    private fun provideProductRemoteDataSource(): ProductRemoteDataSource {
        return ProductRemoteDataSource.getInstance(provideApiService())
    }

    private fun provideProductRepository(): ProductRepository {
        return ProductRepository.getInstance(provideProductRemoteDataSource())
    }

    private fun providePaymentMethodRemoteDataSource(): PaymentMethodRemoteDataSource {
        return PaymentMethodRemoteDataSource.getInstance(provideApiService())
    }

    private fun providePaymentMethodRepository(): PaymentMethodRepository {
        return PaymentMethodRepository.getInstance(providePaymentMethodRemoteDataSource())
    }

    private fun provideOrderRemoteDataSource(): OrderRemoteDataSource {
        return OrderRemoteDataSource.getInstance(provideApiService())
    }

    private fun provideOrderRepository(): OrderRepository {
        return OrderRepository.getInstance(provideOrderRemoteDataSource())
    }

    private fun provideCartRemoteDataSource(): CartRemoteDataSource {
        return CartRemoteDataSource.getInstance(provideApiService())
    }

    private fun provideCartRepository(): CartRepository {
        return CartRepository.getInstance(provideCartRemoteDataSource())
    }


    fun provideViewModelFactory(): ViewModelFactory {
        return ViewModelFactory.getInstance(
            provideUserRepository(),
            provideBrandRepository(),
            provideProductRepository(),
            providePaymentMethodRepository(),
            provideOrderRepository(),
            provideCartRepository()
        )
    }
}
