package com.skincarean.android.core.data.source.remote.network

import android.telecom.CallEndpoint
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import com.skincarean.android.core.data.source.remote.request.CartOrderRequest
import com.skincarean.android.core.data.source.remote.request.CartRequest
import com.skincarean.android.core.data.source.remote.request.DirectlyOrderRequest
import com.skincarean.android.core.data.source.remote.request.GoogleTokenRequest
import com.skincarean.android.core.data.source.remote.request.LoginUserRequest
import com.skincarean.android.core.data.source.remote.request.RegisterUserRequest
import com.skincarean.android.core.data.source.remote.request.UpdateUserRequest
import com.skincarean.android.core.data.source.remote.response.OrderResponse
import com.skincarean.android.core.data.source.remote.response.brand.BrandResponse
import com.skincarean.android.core.data.source.remote.response.cart.CartResponse
import com.skincarean.android.core.data.source.remote.response.product.DetailProductResponse
import com.skincarean.android.core.data.source.remote.response.login.LoginUserResponse
import com.skincarean.android.core.data.source.remote.response.payment_method.PaymentMethodResponse
import com.skincarean.android.core.data.source.remote.response.product.ProductResponse
import com.skincarean.android.core.data.source.remote.response.review.ReviewResponse
import com.skincarean.android.core.data.source.remote.response.WebResponse
import com.skincarean.android.core.data.source.remote.response.brand.DetailBrandResponse
import com.skincarean.android.core.data.source.remote.response.login.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @POST("api/users/auth/login/google/verify")
    fun verifyToken(@Body token: GoogleTokenRequest): Call<WebResponse<LoginUserResponse>>

    @POST("api/users/auth/register")
    fun register(@Body registerUserRequest: RegisterUserRequest): Call<WebResponse<String>>

    @POST("api/users/auth/login")
    fun login(@Body loginUserRequest: LoginUserRequest): Call<WebResponse<LoginUserResponse>>

    @GET("api/users/current-user")
    fun getCurrentUser(): Call<WebResponse<UserResponse>>

    @FormUrlEncoded
    @PATCH("api/users/current-user")
    fun updateUser(
        @Field("fullName") fullName: String?,
        @Field("address") address: String?,
        @Field("phone") phone: String?,
        @Field("email") email: String?,

        ): Call<WebResponse<UserResponse>>

    @DELETE("api/users/current-user")
    fun logout(): Call<WebResponse<String>>

    @GET("api/brands/top-brands")
    fun getBrandsByTopBrand(): Call<WebResponse<List<BrandResponse>>>

    @GET("api/brands/{brandId}")
    fun getDetailBrandByBrandId(@Path("brandId") brandId: Long): Call<WebResponse<DetailBrandResponse>>

    @GET("api/products/popular-products")
    fun getAllTopProducts(): Call<WebResponse<List<ProductResponse>>>

    @GET("api/products/{productId}")
    fun getProductByProductId(@Path("productId") productId: String): Call<WebResponse<DetailProductResponse>>

    @GET("api/products/{productId}/reviews")
    fun getAllReviewByProductId(@Path("productId") productId: String): Call<WebResponse<List<ReviewResponse>>>

    @GET("api/products")
    fun getAllProducts(): Call<WebResponse<List<ProductResponse>>>

    @GET("api/brands/{brandId}/products")
    fun getAllProductsByBrand(@Path("brandId") brandId: Long): Call<WebResponse<List<ProductResponse>>>

    @GET("api/products/search")
    fun searchProducts(
        @Query("name") nameProduct: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Call<WebResponse<List<ProductResponse>>>

    @GET("api/payment-methods")
    fun getPaymentMethods(): Call<WebResponse<List<PaymentMethodResponse>>>

    @POST("api/orders/checkout/direct")
    fun directlyOrder(@Body directlyOrderRequest: DirectlyOrderRequest): Call<WebResponse<Map<String, Any>>>

    @POST("api/orders/cart/checkout")
    fun cartOrder(@Body cartOrderRequest: CartOrderRequest): Call<WebResponse<Map<String, Any>>>

    @GET("api/orders")
    fun getAllOrders(): Call<WebResponse<List<OrderResponse>>>

    @GET("api/orders/{orderId}")
    fun getDetailOrder(@Path("orderId") orderId: String): Call<WebResponse<OrderResponse>>

    @GET("api/orders/complete-order")
    fun getAllCompleteOrders(): Call<WebResponse<List<OrderResponse>>>

    @GET("api/orders/pending-order")
    fun getAllPendingOrders(): Call<WebResponse<List<OrderResponse>>>

    @GET("api/orders/cancel-order")
    fun getAllCancelOrders(): Call<WebResponse<List<OrderResponse>>>

    @POST("api/carts")
    fun addProductToCart(@Body addCartRequest: CartRequest): Call<WebResponse<String>>

    @GET("api/carts")
    fun getAllCart(): Call<WebResponse<CartResponse>>

    @GET("api/carts/active-carts")
    fun getAllActiveCarts(): Call<WebResponse<CartResponse>>

    @PATCH("api/carts/{cartId}")
    fun setActiveCart(@Path("cartId") cartId : Long) :Call<WebResponse<String>>

    @POST("api/carts/{cartId}/plus-quantity")
    fun plusQuantity(@Path("cartId") cartId: Long): Call<WebResponse<String>>

    @DELETE("api/carts/{cartId}/minus-quantity")
    fun minusQuantity(@Path("cartId") cartId: Long): Call<WebResponse<String>>

    @DELETE("api/carts/{cartId}")
    fun deleteCartItem(@Path("cartId") cartId: Long): Call<WebResponse<String>>

    @DELETE("api/carts")
    fun deleteAllCartItem(): Call<WebResponse<String>>
}