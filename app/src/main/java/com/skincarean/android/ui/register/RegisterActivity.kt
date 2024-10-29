package com.skincarean.android.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.skincarean.android.Utilities
import com.skincarean.android.core.data.di.Injector
import com.skincarean.android.core.data.source.remote.UserRemoteDataSource
import com.skincarean.android.core.data.source.remote.request.RegisterUserRequest
import com.skincarean.android.databinding.ActivityRegisterBinding
import com.skincarean.android.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModelFactory = Injector.provideViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)

        register()
        binding.tvRegisterLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            finish()
            startActivity(intent)
        }

        setupObservers()
    }

    private fun register() {
        binding.btnRegister.setOnClickListener {
            val edtUsername = binding.edtRegisterUsername
            val edtFullName = binding.edtRegisterFullname
            val edtPassword = binding.edtRegisterPassword
            val edtConfirmPassword = binding.edtRegisterConfirmPassword
            val edtEmail = binding.edtRegisterEmail
            val edtAddress = binding.edtRegisterAddress
            val edtPhone = binding.edtRegisterPhone

            var isError = false
            if (edtUsername.text.isBlank()) {
                edtUsername.error = "Harap masukkan username"
                isError = true
            }
            if (edtPassword.text.isBlank()) {
                edtPassword.error = "Harap masukkan password"
                isError = true
            }
            if (edtConfirmPassword.text.isBlank()) {
                edtConfirmPassword.error = "Harap masukkan konfirmasi password"
                isError = true
            }
            if (edtEmail.text.isBlank()) {
                edtEmail.error = "Harap masukkan email"
                isError = true
            }
            if (edtFullName.text.isBlank()) {
                edtFullName.error = "Harap masukkan nama lengkap"
                isError = true
            }


            if (!isError) {
                val registerUserRequest = RegisterUserRequest(
                    username = edtUsername.text.toString(),
                    password = edtPassword.text.toString(),
                    confirmPassword = edtConfirmPassword.text.toString(),
                    email = edtEmail.text.toString(),
                    address = edtAddress.text.toString(),
                    phone = edtPhone.text.toString(),
                    fullName = edtFullName.text.toString()

                )

                viewModel.registerUser(registerUserRequest)


            }

        }
    }

    private fun setupObservers() {
        viewModel.registerResult.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {
                Utilities.customDialog(it, this)
            }


        })
        viewModel.errorMessage.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {
                Utilities.customDialog(it, this)
            }
        })
    }

}