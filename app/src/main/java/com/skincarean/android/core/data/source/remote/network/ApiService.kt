package com.skincarean.android.core.data.source.remote.network

import com.skincarean.android.core.data.source.remote.request.CartOrderRequest
import com.skincarean.android.core.data.source.remote.request.CartRequest
import com.skincarean.android.core.data.source.remote.request.DirectlyOrderRequest
import com.skincarean.android.core.data.source.remote.request.GoogleTokenRequest
import com.skincarean.android.core.data.source.remote.request.LoginUserRequest
import com.skincarean.android.core.data.source.remote.request.RegisterUserRequest
import com.skincarean.android.core.data.source.remote.response.OrderResponse
import com.skincarean.android.core.data.source.remote.response.brand.BrandResponse
import com.skincarean.android.core.data.source.remote.response.cart.CartResponse
import com.skincarean.android.core.data.source.remote.response.product.DetailProductResponse
import com.skincarean.android.core.data.source.remote.response.login.LoginUserResponse
import com.skincarean.android.core.data.source.remote.response.payment_method.PaymentMethodResponse
import com.skincarean.android.core.data.source.remote.response.product.ProductResponse
import com.skincarean.android.core.data.source.remote.response.review.ReviewResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    @POST("api/users/auth/login/google/verify")
    fun verifyToken(@Body token: GoogleTokenRequest): Call<WebResponse<LoginUserResponse>>

    @POST("api/users/auth/register")
    fun register(@Body registerUserRequest: RegisterUserRequest): Call<WebResponse<String>>

    @POST("api/users/auth/login")
    fun login(@Body loginUserRequest: LoginUserRequest): Call<WebResponse<LoginUserResponse>>

    @GET("api/brands/top-brands")
    fun getBrandsByTopBrand(): Call<WebResponse<List<BrandResponse>>>

    @GET("api/products/popular-products")
    fun getAllTopProducts(): Call<WebResponse<List<ProductResponse>>>

    @GET("api/products/{productId}")
    fun getProductByProductId(@Path("productId") productId: String): Call<WebResponse<DetailProductResponse>>

    @GET("api/products/{productId}/reviews")
    fun getAllReviewByProductId(@Path("productId") productId: String): Call<WebResponse<List<ReviewResponse>>>

    @GET("api/products")
    fun getAllProducts(): Call<WebResponse<List<ProductResponse>>>

    @GET("api/payment-methods")
    fun getPaymentMethods(): Call<WebResponse<List<PaymentMethodResponse>>>

    @POST("api/orders/checkout/product")
    fun directlyOrder(@Body directlyOrderRequest: DirectlyOrderRequest): Call<WebResponse<Map<String, Any>>>

    @POST("api/orders/checkout/cart")
    fun cartOrder(@Body cartOrderRequest: CartOrderRequest): Call<WebResponse<Map<String, Any>>>

    @GET("api/orders")
    fun getAllOrders(): Call<WebResponse<List<OrderResponse>>>

    @GET("api/orders/{orderId}")
    fun getDetailOrder(@Path("orderId") orderId: String): Call<WebResponse<OrderResponse>>

    @GET("api/orders/complete-order")
    fun getAllCompleteOrders(): Call<WebResponse<List<OrderResponse>>>

    @GET("api/orders/pending-order")
    fun getAllPendingOrders(): Call<WebResponse<List<OrderResponse>>>

    @POST("api/carts")
    fun addProductToCart(@Body addCartRequest: CartRequest): Call<WebResponse<String>>

    @GET("api/carts")
    fun getAllCart(): Call<WebResponse<CartResponse>>

    @POST("api/carts/{cartId}/plus-quantity")
    fun plusQuantity(@Path("cartId") cartId: Long): Call<WebResponse<String>>

    @DELETE("api/carts/{cartId}/minus-quantity")
    fun minusQuantity(@Path("cartId") cartId: Long): Call<WebResponse<String>>

    @DELETE("api/carts/{cartId}")
    fun deleteCartItem(@Path("cartId") cartId: Long): Call<WebResponse<String>>

    @DELETE("api/carts")
    fun deleteAllCartItem(): Call<WebResponse<String>>
}