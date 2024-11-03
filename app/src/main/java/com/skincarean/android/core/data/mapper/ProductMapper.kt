package com.skincarean.android.core.data.mapper

import com.skincarean.android.core.data.domain.model.product.DetailProduct
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.core.data.domain.model.product.ProductImageItem
import com.skincarean.android.core.data.domain.model.product.ProductVariant
import com.skincarean.android.core.data.source.remote.response.product.DetailProductResponse
import com.skincarean.android.core.data.source.remote.response.product.ProductResponse
import java.util.stream.Collectors

object ProductMapper {
    fun listProductResponseToListProduct(input: List<ProductResponse>): List<Product> {
        val products = input.stream().map {
            Product(
                brandName = it.brandName,
                productId = it.productId,
                productName = it.productName,
                isPromo = it.isPromo,
                categoryName = it.categoryName,
                isPopularProduct = it.isPopularProduct,
                thumbnailImage = it.thumbnailImage,
                minPrice = it.minPrice,
                maxPrice = it.maxPrice,
                firstDiscount = it.firstDiscount,
                firstOriginalPrice = it.firstOriginalPrice,
                firstPrice = it.firstPrice
            )
        }.collect(Collectors.toList())
        return products
    }

    fun detailProductResponseToDetailProduct(input: DetailProductResponse): DetailProduct {


        val productVariants =
            input.productVariantResponses?.stream()?.map { productVariantResponse ->

                val productImageItems =
                    productVariantResponse?.productVariantImages?.map { productImageItemResponse ->

                        ProductImageItem(
                            imageUrl = productImageItemResponse?.imageUrl,
                            id = productImageItemResponse?.id
                        )
                    }

                ProductVariant(

                    id = productVariantResponse?.id,
                    size = productVariantResponse?.size,
                    stok = productVariantResponse?.stok,
                    price = productVariantResponse?.price,
                    originalPrice = productVariantResponse?.originalPrice,
                    discount = productVariantResponse?.discount,
                    productImageItems = productImageItems,
                    thumbnailVariantImage = productVariantResponse?.thumbnailVariantImage

                )

            }?.collect(Collectors.toList())

        return DetailProduct(
            brandName = input.brandName,
            productId = input.productId,
            productName = input.productName,
            totalStok = input.totalStok,
            isPromo = input.isPromo,
            categoryName = input.categoryName,
            bpomCode = input.bpomCode,
            isPopularProduct = input.isPopularProduct,
            thumbnailImage = input.thumbnailImage,
            ingredient = input.ingredient,
            productDescription = input.productDescription,
            productVariants = productVariants,
            maxPrice = input.maxPrice,
            minPrice = input.minPrice


        )
    }
}