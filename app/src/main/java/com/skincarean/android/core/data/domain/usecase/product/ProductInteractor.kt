package com.skincarean.android.core.data.domain.usecase.product

import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.product.DetailProduct
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.core.data.repository.ProductRepository

class ProductInteractor(private val productRepository: ProductRepository) : ProductUseCase {
    override fun getAllPopularProduct(callback: (Resource<List<Product>>) -> Unit) {
        productRepository.getAllPopularProduct(callback)
    }

    override fun getAllProducts(callback: (Resource<List<Product>>) -> Unit) {
        productRepository.getAllProducts(callback)
    }

    override fun getDetailProductById(productId : String, callback: (Resource<DetailProduct>) -> Unit) {
        productRepository.getDetailProductById(productId, callback)
    }
}