package com.skincarean.android.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skincarean.android.core.data.domain.usecase.brand.BrandUseCase
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
    private val userRepository: UserRepository,
    private val brandRepository: BrandRepository,
    private val productRepository: ProductRepository,
    private val paymentMethodRepository: PaymentMethodRepository,
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val userUseCase: UserUseCase,
    private val paymentMethodUseCase: PaymentMethodUseCase,
    private val brandUseCase: BrandUseCase,
    private val productUseCase: ProductUseCase,
    private val orderUseCase: OrderUseCase,
) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(
            userRepository: UserRepository,
            brandRepository: BrandRepository,
            productRepository: ProductRepository,
            paymentMethodRepository: PaymentMethodRepository,
            orderRepository: OrderRepository,
            cartRepository: CartRepository,
            userUseCase: UserUseCase,
            paymentMethodUseCase: PaymentMethodUseCase,
            brandUseCase: BrandUseCase,
            productUseCase: ProductUseCase,
            orderUseCase: OrderUseCase,
        ): ViewModelFactory {
            synchronized(this) {
                instance = ViewModelFactory(
                    userRepository,
                    brandRepository,
                    productRepository,
                    paymentMethodRepository,
                    orderRepository,
                    cartRepository,
                    userUseCase,
                    paymentMethodUseCase,
                    brandUseCase,
                    productUseCase,
                    orderUseCase
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
                return LoginViewModel(userRepository, userUseCase) as T
            }

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                return HomeViewModel(brandRepository, productRepository, brandUseCase) as T
            }

            modelClass.isAssignableFrom(ProductViewModel::class.java) -> {
                return ProductViewModel(productRepository, productUseCase) as T
            }

            modelClass.isAssignableFrom(CheckoutViewModel::class.java) -> {
                return CheckoutViewModel(
                    productUseCase,
                    paymentMethodUseCase,
                    orderRepository,
                    orderUseCase
                ) as T
            }

            modelClass.isAssignableFrom(CartViewModel::class.java) -> {
                return CartViewModel(cartRepository) as T
            }

            modelClass.isAssignableFrom(OrderViewModel::class.java) -> {
                return OrderViewModel(orderRepository,orderUseCase) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                return ProfileViewModel(userRepository, userUseCase) as T
            }


            else -> {
                throw Throwable("Unknown Viewmodel class : " + modelClass.name)
            }
        }
    }

}