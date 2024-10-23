package com.skincarean.android

import android.util.Log
import com.skincarean.android.core.data.domain.model.PaymentMethod
import com.skincarean.android.core.data.source.remote.response.OrderResponse
import com.skincarean.android.core.data.source.remote.response.brand.BrandResponse
import com.skincarean.android.core.data.source.remote.response.payment_method.PaymentMethodResponse
import com.skincarean.android.core.data.source.remote.response.product.ProductResponse

interface OnItemClickCallback {
    fun onClicked(cartId: Long) {
        Log.d("OnItemClickCallback", "Clicked for cartId : $cartId")
    }

    fun onProductClickCallback(data: ProductResponse) {
        Log.d("OnItemClickCallback", "this is contain $data")
    }

    fun onTopBrandClickCallback(data : BrandResponse){
        Log.d("OnItemClickCallback", "this is contain $data")
    }

    fun onOrderClickCallback(data : OrderResponse){
        Log.d("OnItemClickCallback", "this is contain $data")
    }

    fun onPaymentMethodClickCallback(data: PaymentMethod) {
        Log.d("OnItemClickCallback", "this is contain $data")
    }

    fun onPlusClicked(cartId: Long) {
        Log.d("OnItemClickCallback", "Plus clicked for cartId : $cartId")
    }

    fun onMinusClicked(cartId: Long) {
        Log.d("OnItemClickCallback", "Minus clicked for cartId : $cartId ")
    }

    fun onTrashCartItemClicked(cartId: Long) {
        Log.d("OnItemClickCallback", "Trash item clicked for cartId : $cartId")
    }
}