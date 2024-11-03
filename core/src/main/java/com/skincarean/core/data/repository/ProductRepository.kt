package com.skincarean.android.core.data.repository



import com.skincarean.android.core.data.domain.model.product.DetailProduct
import com.skincarean.android.core.data.domain.model.product.Product

import com.skincarean.android.core.data.domain.model.product.ProductVariant
import com.skincarean.android.core.data.domain.model.product.Review
import com.skincarean.android.core.data.domain.repository.IProductRepository
import com.skincarean.android.core.data.mapper.ProductMapper
import com.skincarean.android.core.data.source.remote.ApiResponse
import com.skincarean.android.core.data.source.remote.ProductRemoteDataSource
import com.skincarean.core.Resource
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
                        val products =
                            ProductMapper.listProductResponseToListProduct(productResponses)

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

    override fun getDetailProductById(
        productId: String,
        callback: (Resource<DetailProduct>) -> Unit,
    ) {
        productRemoteDataSource.getDetailProductById(productId) { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let { detailProductResponse ->
                        val detailProduct =
                            ProductMapper.detailProductResponseToDetailProduct(detailProductResponse)
                        callback(Resource.Success(detailProduct))
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

    override fun searchProducts(
        nameProduct: String,
        page: Int,
        size: Int,
        callback: (Resource<List<Product>>) -> Unit,
    ) {
        productRemoteDataSource.searchProduct(nameProduct, page, size) { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let { productResponses ->
                        val products =
                            ProductMapper.listProductResponseToListProduct(productResponses)
                        callback(Resource.Success(products))
                    }
                }

                is ApiResponse.Error -> {
                    apiResponse.errorMessage?.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("No data available"))
                }
            }
        }
    }

    override fun getDetailProductByProductIdAndVariantId(
        productId: String,
        variantId: Long,
        callback: (Resource<DetailProduct>) -> Unit,
    ) {
        productRemoteDataSource.getDetailProductByProductIdAndVariantId(
            productId,
            variantId
        ) { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let { detailProductResponseBySingleVariant ->
                        val productVariantResponse =
                            detailProductResponseBySingleVariant.productVariant
                        val productVariant = ProductVariant(
                            id = productVariantResponse?.id,
                            size = productVariantResponse?.size,
                            stok = productVariantResponse?.stok,
                            price = productVariantResponse?.price,
                            originalPrice = productVariantResponse?.originalPrice,
                            discount = productVariantResponse?.discount,
                            thumbnailVariantImage = productVariantResponse?.thumbnailVariantImage,
                        )
                        val detailProduct = DetailProduct(
                            brandName = detailProductResponseBySingleVariant.brandName,
                            productId = detailProductResponseBySingleVariant.productId,
                            productName = detailProductResponseBySingleVariant.productName,
                            categoryName = detailProductResponseBySingleVariant.categoryName,
                            productVariant = productVariant,
                            isPopularProduct = detailProductResponseBySingleVariant.isPopularProduct,
                            isPromo = detailProductResponseBySingleVariant.isPromo,
                            ingredient = detailProductResponseBySingleVariant.ingredient,
                            thumbnailImage = detailProductResponseBySingleVariant.thumbnailImage,
                            productDescription = detailProductResponseBySingleVariant.productDescription,
                            totalStok = detailProductResponseBySingleVariant.totalStok,
                            bpomCode = detailProductResponseBySingleVariant.bpomCode
                        )
                        callback(Resource.Success(detailProduct))
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


    override fun getAllReviewsByProductId(
        productId: String,
        callback: (Resource<List<Review>>) -> Unit,
    ) {
        productRemoteDataSource.getallReviewByProductId(productId) { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let { reviewResponses ->

                        val reviews = reviewResponses.stream().map { reviewResponse ->
                            Review(
                                reviewResponse.createdAt,
                                reviewResponse.review,
                                reviewResponse.photoProfileUser,
                                reviewResponse.isRecommended,
                                reviewResponse.rating,
                                reviewResponse.usagePeriod,
                                reviewResponse.fullNameUser,
                                reviewResponse.reviewId
                            )
                        }.collect(Collectors.toList())
                        callback(Resource.Success(reviews))
                    }
                }

                is ApiResponse.Error -> {
                    apiResponse.errorMessage?.let {
                        callback(Resource.Error(it))
                    }
                }

                is ApiResponse.Empty -> {
                    callback(Resource.Error("No data available"))
                }
            }
        }
    }


    override fun getAllProducts(callback: (Resource<List<Product>>) -> Unit) {
        productRemoteDataSource.getAllProducts { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    apiResponse.data.data?.let { listProductResponses ->

                        val products =
                            ProductMapper.listProductResponseToListProduct(listProductResponses)
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


}