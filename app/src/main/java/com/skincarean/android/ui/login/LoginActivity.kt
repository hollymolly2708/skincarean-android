package com.skincarean.android.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.skincarean.android.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        login()


    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            if(binding.edtUsername.text.toString().isBlank()){
                binding.edtUsername.error = "Username perlu diisi"
            }
            if(binding.edtPassword.text.toString().isBlank()){
                binding.edtPassword.error = "Password perlu diisi"
            }


        }
    }
}