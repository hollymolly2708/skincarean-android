package com.skincarean.core.data.di

import android.content.Context
import com.skincarean.android.core.data.domain.usecase.brand.BrandInteractor
import com.skincarean.android.core.data.domain.usecase.brand.BrandUseCase
import com.skincarean.android.core.data.domain.usecase.cart.CartInteractor
import com.skincarean.android.core.data.domain.usecase.cart.CartUseCase
import com.skincarean.android.core.data.domain.usecase.order.OrderInteractor
import com.skincarean.android.core.data.domain.usecase.order.OrderUseCase
import com.skincarean.android.core.data.domain.usecase.payment_method.PaymentMethodInteractor
import com.skincarean.android.core.data.domain.usecase.product.ProductInteractor
import com.skincarean.android.core.data.domain.usecase.product.ProductUseCase
import com.skincarean.android.core.data.domain.usecase.user.UserInteractor
import com.skincarean.android.core.data.domain.usecase.user.UserUseCase
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
import com.skincarean.core.LoginSharedPreferences

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
        LoginSharedPreferences.getToken(context)
    }

    private fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val token = LoginSharedPreferences.getToken(appContext)
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
                if (!LoginSharedPreferences.getToken(appContext).isNullOrEmpty()) {
                    LoginSharedPreferences.clear(appContext)
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
            .baseUrl("http://192.168.144.184:8080/")
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


    fun provideUserInteractor(): UserUseCase {
        return UserInteractor(provideUserRepository())
    }


    private fun provideBrandRemoteDataSource(): BrandRemoteDataSource {
        return BrandRemoteDataSource.getInstance(provideApiService())
    }

    private fun provideBrandRepository(): BrandRepository {
        return BrandRepository.getInstance(provideBrandRemoteDataSource())
    }

    fun provideBrandInteractor(): BrandUseCase {
        return BrandInteractor(provideBrandRepository())
    }

    private fun provideProductRemoteDataSource(): ProductRemoteDataSource {
        return ProductRemoteDataSource.getInstance(provideApiService())
    }

    private fun provideProductRepository(): ProductRepository {
        return ProductRepository.getInstance(provideProductRemoteDataSource())
    }

    fun provideProductInteractor(): ProductUseCase {
        return ProductInteractor(provideProductRepository())
    }

    private fun providePaymentMethodRemoteDataSource(): PaymentMethodRemoteDataSource {
        return PaymentMethodRemoteDataSource.getInstance(provideApiService())
    }

    private fun providePaymentMethodRepository(): PaymentMethodRepository {
        return PaymentMethodRepository.getInstance(providePaymentMethodRemoteDataSource())
    }

    fun providePaymentMethodInteractor(): PaymentMethodInteractor {
        return PaymentMethodInteractor(providePaymentMethodRepository())
    }

    private fun provideOrderRemoteDataSource(): OrderRemoteDataSource {
        return OrderRemoteDataSource.getInstance(provideApiService())
    }

    private fun provideOrderRepository(): OrderRepository {
        return OrderRepository.getInstance(provideOrderRemoteDataSource())
    }

    fun provideOrderInteractor(): OrderUseCase {
        return OrderInteractor(provideOrderRepository())
    }

    private fun provideCartRemoteDataSource(): CartRemoteDataSource {
        return CartRemoteDataSource.getInstance(provideApiService())
    }

    private fun provideCartRepository(): CartRepository {
        return CartRepository.getInstance(provideCartRemoteDataSource())
    }

    fun provideCartInteractor(): CartUseCase {
        return CartInteractor(provideCartRepository())
    }


}