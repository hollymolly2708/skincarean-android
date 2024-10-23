package com.skincarean.android

import android.util.Log
import com.skincarean.android.core.data.domain.model.brand.Brand
import com.skincarean.android.core.data.domain.model.payment_method.PaymentMethod
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.core.data.source.remote.response.OrderResponse

interface OnItemClickCallback {
    fun onClicked(cartId: Long) {
        Log.d("OnItemClickCallback", "Clicked for cartId : $cartId")
    }

    fun onProductClickCallback(data: Product) {
        Log.d("OnItemClickCallback", "this is contain $data")
    }

    fun onTopBrandClickCallback(data : Brand){
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