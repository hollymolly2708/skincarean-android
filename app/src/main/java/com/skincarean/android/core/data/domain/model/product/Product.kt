package com.skincarean.android.core.data.domain.model.product

import java.math.BigDecimal

data class Product(
    val brandName: String? = null,
    val productId: String? = null,
    val originalPrice: BigDecimal? = null,
    val discount: BigDecimal? = null,
    val productName : String? = null,
    val stok: Int? = null,
    val isPromo: Boolean? = null,
    val categoryName: String? = null,
    val bpomCode: String? = null,
    val size: String? = null,
    val price: BigDecimal? = null,
    val isPopularProduct: Boolean? = null,
    val thumbnailImage: String? = null,
    val productImage: List<ProductImageItem?>? = null,
    val productDescription: String? = null,
)
