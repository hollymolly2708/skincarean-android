package com.skincarean.android.core.data.repository

import com.skincarean.android.core.data.source.remote.ProductRemoteDataSource
import com.skincarean.android.core.data.source.remote.network.ApiService

class ProductRepository private constructor(private val productRemoteDataSource: ProductRemoteDataSource) {

    companion object {
        var instance: ProductRepository? = null
        fun getInstance(productRemoteDataSource: ProductRemoteDataSource): ProductRepository {
            if (instance == null) {
                synchronized(this) {
                    instance = ProductRepository(productRemoteDataSource)
                }
            }
            return instance!!
        }
    }

    fun getAllPopularProduct(callback: (Any) -> Unit) {
        productRemoteDataSource.getAllPopularProducts(callback)
    }

    fun getProductByProductId(productId: String, callback: (Any) -> Unit) {
        productRemoteDataSource.getProductByProductId(productId, callback)
    }

    fun getAllReviewsByProductId(productId: String, callback: (Any) -> Unit) {
        productRemoteDataSource.getallReviewByProductId(productId, callback)
    }

    fun getAllProducts(callback: (Any) -> Unit) {
        productRemoteDataSource.getAllProducts(callback)
    }

    fun searchProducts(nameProduct: String, page: Int, size: Int, callback: (Any) -> Unit) {
        productRemoteDataSource.searchProduct(nameProduct, page, size, callback)
    }
}