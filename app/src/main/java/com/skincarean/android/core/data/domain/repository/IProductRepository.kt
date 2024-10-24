package com.skincarean.android.core.data.domain.repository


import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.product.DetailProduct
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.core.data.domain.model.product.Review

interface IProductRepository {
    fun getAllPopularProduct(callback: (Resource<List<Product>>) -> Unit)
    fun getAllProducts(callback: (Resource<List<Product>>) -> Unit)
    fun getDetailProductById(productId: String, callback: (Resource<DetailProduct>) -> Unit)
    fun searchProducts(
        nameProduct: String,
        page: Int,
        size: Int,
        callback: (Resource<List<Product>>) -> Unit,
    )

    fun getAllReviewsByProductId(productId: String, callback: (Resource<List<Review>>) -> Unit)
}