package com.skincarean.android.core.data.mapper

import com.skincarean.android.Resource
import com.skincarean.android.core.data.domain.model.product.DetailProduct
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.core.data.domain.model.product.ProductImageItem
import com.skincarean.android.core.data.source.remote.response.product.DetailProductResponse
import com.skincarean.android.core.data.source.remote.response.product.ProductResponse
import java.util.stream.Collectors

object ProductMapper {
    fun listProductResponseToListProduct(input: List<ProductResponse>): List<Product> {
        val products = input.stream().map {
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
        return products
    }

    fun detailProductResponseToDetailProduct(input: DetailProductResponse): DetailProduct {
        val productImageResponse = input.productImage
        val productImageItems = productImageResponse?.stream()?.map { imageItem ->
            ProductImageItem(imageItem?.imageUrl, imageItem?.id)
        }?.collect(Collectors.toList())

        val detailProduct = DetailProduct(
            input.brandName,
            input.productId,
            input.originalPrice,
            input.discount,
            input.productName,
            input.stok,
            input.isPromo,
            input.categoryName,
            input.bpomCode,
            input.size,
            input.price,
            input.isPopularProduct,
            input.thumbnailImage,
            productImageItems,
            input.ingredient,
            input.productDescription


        )
        return detailProduct
    }
}