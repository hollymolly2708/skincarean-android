package com.skincarean.android.core.data.domain.usecase.product


import com.skincarean.android.core.data.domain.model.product.DetailProduct
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.core.data.domain.model.product.Review
import com.skincarean.core.Resource

interface ProductUseCase {
    fun getAllPopularProduct(callback: (Resource<List<Product>>) -> Unit)
    fun getAllProducts(callback: (Resource<List<Product>>) -> Unit)
    fun getDetailProductById(productId: String, callback: (Resource<DetailProduct>) -> Unit)
    fun searchProduct(
        nameProduct: String,
        page: Int,
        size: Int,
        callback: (Resource<List<Product>>) -> Unit,
    )


    fun getDetailProductByProductIdAndVariantId(productId : String, variantId : Long, callback: (Resource<DetailProduct>) -> Unit)
    fun getAllReviewsByProductId(productId: String, callback: (Resource<List<Review>>) -> Unit)
}