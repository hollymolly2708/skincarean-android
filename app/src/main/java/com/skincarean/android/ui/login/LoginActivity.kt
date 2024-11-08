package com.skincarean.android.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import com.skincarean.android.LoginSession
import com.skincarean.android.R
import com.skincarean.android.Utilities
import com.skincarean.core.LoginSharedPreferences
import com.skincarean.android.di.AppInjector
import com.skincarean.android.core.data.source.remote.request.LoginUserRequest
import com.skincarean.android.databinding.ActivityLoginBinding
import com.skincarean.android.ui.main.MainActivity
import com.skincarean.android.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var mGoogleApiClient: GoogleApiClient


    companion object {
        const val RC_SIGN_IN = 123;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        login()
        val factory = AppInjector.provideViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        setupObservers()

        //pengecekan session
        LoginSession.checkSession(this)

        connectToGoogleSignIn()

        binding.btnLoginViaGmail.setOnClickListener {
            intentToGoogleAccounts()
        }
        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.layoutLogin.visibility = View.VISIBLE
        binding.loadingLogin.visibility =View.GONE


    }

    private fun login() {
        binding.btnLogin.setOnClickListener {

            val edtUsername = binding.edtUsername
            val edtPassword = binding.edtPassword
            var isError = false
            if (edtUsername.text.toString().isBlank()) {
                binding.edtUsername.error = "Username perlu diisi"
                isError = true

            }
            if (edtPassword.text.toString().isBlank()) {
                binding.edtPassword.error = "Password perlu diisi"
                isError = true
            }

            val loginUserRequest =
                LoginUserRequest(edtUsername.text.toString(), edtPassword.text.toString())

            if (!isError) {
                viewModel.login(loginUserRequest)
            }

        }
    }

    private fun setupObservers() {
        viewModel.loginResult.observe(this) { response ->
            if (response != null) {
                LoginSharedPreferences.saveData(this, response)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage != null) {
                Utilities.customDialog(errorMessage, this)
            }
        }
        viewModel.loading.observe(this){
            loading(it)
        }
    }


    private fun loading(loading : Boolean){
        if(loading){
            binding.layoutLogin.visibility = View.GONE
            binding.loadingLogin.visibility = View.VISIBLE
        }else{
            binding.layoutLogin.visibility = View.VISIBLE
            binding.loadingLogin.visibility =View.GONE
        }
    }

    private fun connectToGoogleSignIn() {

        // 1  Membuat konfigurasi Google sign-in apa yang pengen diminta dari google akun
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestProfile()
            .requestEmail()
            .requestIdToken(getString(R.string.default_web_client_id))
            .build()

        //  2 Membuat koneksi antara aplikasi dengan Google Api Client salah satunya Google Sign-In Api (note: google api ada banyak salah satunya google sign in)
        mGoogleApiClient =
            GoogleApiClient.Builder(this@LoginActivity)
                .enableAutoManage(
                    this@LoginActivity, object : OnConnectionFailedListener {
                        override fun onConnectionFailed(p0: ConnectionResult) {
                            Toast.makeText(
                                this@LoginActivity,
                                "Koneksi ke akun google gagal",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
    }

    private fun intentToGoogleAccounts() {
        // 3 mencoba intent ke activity google sign in dengan startActivityForResult
        val intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            // 4 mendapatkan result dari google sign in berupa data
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
            if (result!!.isSuccess) {
                // 5 ambil data akun yang login
                val account = result.signInAccount
                if (account != null) {
                    // 6 kirim ke backend id tokennya
                    sendTokenToBackend(account.idToken)
                }
            }
        }
    }


    private fun sendTokenToBackend(idToken: String?) {
        viewModel.loginViaGoogle(idToken)
    }
}