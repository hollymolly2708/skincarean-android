package com.skincarean.android.core.data.domain.repository


import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.product.DetailProduct
import com.skincarean.android.core.data.domain.model.product.Product

interface IProductRepository {
    fun getAllPopularProduct(callback: (Resource<List<Product>>) -> Unit)
    fun getAllProducts(callback: (Resource<List<Product>>) -> Unit)
    fun getDetailProductById(productId: String, callback: (Resource<DetailProduct>) -> Unit)
}