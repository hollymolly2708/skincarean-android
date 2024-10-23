package com.skincarean.android.core.data.repository


import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.product.DetailProduct
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.core.data.domain.model.product.ProductImageItem
import com.skincarean.android.core.data.domain.repository.IProductRepository
import com.skincarean.android.core.data.source.remote.ApiResponse
import com.skincarean.android.core.data.source.remote.ProductRemoteDataSource
import java.util.stream.Collectors

class ProductRepository private constructor(private val productRemoteDataSource: ProductRemoteDataSource) :
    IProductRepository {

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

    override fun getAllPopularProduct(callback: (Resource<List<Product>>) -> Unit) {
        productRemoteDataSource.getAllPopularProducts { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let { productResponses ->
                        val products = productResponses.stream().map {

                            val productImageItemResponse = it.productImage
                            val productImageItems =
                                productImageItemResponse?.stream()?.map { imageItem ->
                                    ProductImageItem(imageItem?.imageUrl, imageItem?.id)
                                }?.collect(Collectors.toList())

                            Product(
                                it.brandName,
                                it.productId,
                                it.originalPrice,
                                it.discount,
                                it.productName,
                                it.stok,
                                it.isPromo,
                                it.categoryName,
                                it.bpomCode,
                                it.size,
                                it.price,
                                it.isPopularProduct,
                                it.thumbnailImage,
                                productImageItems,
                                it.productDescription
                            )

                        }.collect(Collectors.toList())

                        callback(Resource.Success(products))

                    }
                }

                is ApiResponse.Error -> {
                    apiResponse.errorMessage.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("no data available"))
                }
            }
        }
    }

    override fun getDetailProductById(productId: String, callback: (Resource<DetailProduct>) -> Unit) {
        productRemoteDataSource.getDetailProductById(productId) { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let {
                        val productImageResponse = it.productImage
                        val productImageItems = productImageResponse?.stream()?.map { imageItem ->
                            ProductImageItem(imageItem?.imageUrl, imageItem?.id)
                        }?.collect(Collectors.toList())
                        callback(
                            Resource.Success(
                                DetailProduct(
                                    it.brandName,
                                    it.productId,
                                    it.originalPrice,
                                    it.discount,
                                    it.productName,
                                    it.stok,
                                    it.isPromo,
                                    it.categoryName,
                                    it.bpomCode,
                                    it.size,
                                    it.price,
                                    it.isPopularProduct,
                                    it.thumbnailImage,
                                    productImageItems,
                                    it.productDescription
                                )
                            )
                        )
                    }
                }

                is ApiResponse.Error -> {
                    apiResponse.errorMessage.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("no data available"))
                }
            }
        }
    }

    fun getAllReviewsByProductId(productId: String, callback: (Any) -> Unit) {
        productRemoteDataSource.getallReviewByProductId(productId, callback)
    }

    override fun getAllProducts(callback: (Resource<List<Product>>) -> Unit) {
        productRemoteDataSource.getAllProducts { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let { listProductResponses ->

                        val products = listProductResponses.stream().map {
                            val productImageItem = it.productImage
                            val productImageItems = productImageItem?.stream()?.map { itemImage ->
                                ProductImageItem(itemImage?.imageUrl, itemImage?.id)
                            }?.collect(Collectors.toList())
                            Product(
                                it.brandName,
                                it.productId,
                                it.originalPrice,
                                it.discount,
                                it.productName,
                                it.stok,
                                it.isPromo,
                                it.categoryName,
                                it.bpomCode,
                                it.size,
                                it.price,
                                it.isPopularProduct,
                                it.thumbnailImage,
                                productImageItems,
                                it.productDescription
                            )
                        }.collect(Collectors.toList())
                        callback(Resource.Success(products))
                    }
                }

                is ApiResponse.Error -> {
                    apiResponse.errorMessage.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("no data available"))
                }
            }
        }
    }

    fun searchProducts(nameProduct: String, page: Int, size: Int, callback: (Any) -> Unit) {
        productRemoteDataSource.searchProduct(nameProduct, page, size, callback)
    }
}