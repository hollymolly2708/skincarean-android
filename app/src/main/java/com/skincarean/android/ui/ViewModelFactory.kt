package com.skincarean.android.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skincarean.android.core.data.domain.usecase.brand.BrandUseCase
import com.skincarean.android.core.data.domain.usecase.cart.CartUseCase
import com.skincarean.android.core.data.domain.usecase.order.OrderUseCase
import com.skincarean.android.core.data.domain.usecase.payment_method.PaymentMethodUseCase
import com.skincarean.android.core.data.domain.usecase.product.ProductUseCase
import com.skincarean.android.core.data.domain.usecase.user.UserUseCase
import com.skincarean.android.core.data.repository.BrandRepository
import com.skincarean.android.core.data.repository.CartRepository
import com.skincarean.android.core.data.repository.OrderRepository
import com.skincarean.android.core.data.repository.PaymentMethodRepository
import com.skincarean.android.core.data.repository.ProductRepository
import com.skincarean.android.core.data.repository.UserRepository
import com.skincarean.android.ui.cart.CartViewModel
import com.skincarean.android.ui.checkout.CheckoutViewModel
import com.skincarean.android.ui.home.HomeViewModel
import com.skincarean.android.ui.login.LoginViewModel
import com.skincarean.android.ui.order.OrderViewModel
import com.skincarean.android.ui.product.detail.ProductViewModel
import com.skincarean.android.ui.profile.ProfileViewModel
import com.skincarean.android.ui.register.RegisterViewModel


class ViewModelFactory(

    private val userUseCase: UserUseCase,
    private val paymentMethodUseCase: PaymentMethodUseCase,
    private val brandUseCase: BrandUseCase,
    private val productUseCase: ProductUseCase,
    private val orderUseCase: OrderUseCase,
    private val cartUseCase: CartUseCase,
) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(
            userUseCase: UserUseCase,
            paymentMethodUseCase: PaymentMethodUseCase,
            brandUseCase: BrandUseCase,
            productUseCase: ProductUseCase,
            orderUseCase: OrderUseCase,
            cartUseCase: CartUseCase,
        ): ViewModelFactory {
            synchronized(this) {
                instance = ViewModelFactory(
                    userUseCase,
                    paymentMethodUseCase,
                    brandUseCase,
                    productUseCase,
                    orderUseCase,
                    cartUseCase
                )
            }

            return instance!!
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                return RegisterViewModel(userUseCase) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(userUseCase) as T
            }

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                return HomeViewModel(productUseCase, brandUseCase) as T
            }

            modelClass.isAssignableFrom(ProductViewModel::class.java) -> {
                return ProductViewModel(productUseCase) as T
            }

            modelClass.isAssignableFrom(CheckoutViewModel::class.java) -> {
                return CheckoutViewModel(
                    productUseCase,
                    paymentMethodUseCase,
                    orderUseCase
                ) as T
            }

            modelClass.isAssignableFrom(CartViewModel::class.java) -> {
                return CartViewModel(cartUseCase) as T
            }

            modelClass.isAssignableFrom(OrderViewModel::class.java) -> {
                return OrderViewModel(orderUseCase) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                return ProfileViewModel(userUseCase) as T
            }


            else -> {
                throw Throwable("Unknown Viewmodel class : " + modelClass.name)
            }
        }
    }

}