package com.skincarean.android.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import com.skincarean.android.R
import com.skincarean.android.Utilities
import com.skincarean.android.core.data.LoginSharedPref
import com.skincarean.android.core.data.di.Injector
import com.skincarean.android.core.data.source.remote.request.LoginUserRequest
import com.skincarean.android.databinding.ActivityLoginBinding
import com.skincarean.android.ui.ViewModelFactory
import com.skincarean.android.ui.main.MainActivity
import java.util.Objects

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        const val RC_SIGN_IN = 123;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        login()
        val factory = Injector.provideViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        setupObservers()

        //pengecekan session
        LoginSharedPref.checkSession(this)
//        //delete soon
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id)) // client_id dari Firebase
//            .requestEmail()
//            .requestProfile()
//            .build()
//
//        googleSignInClient = GoogleSignIn.getClient(this, gso)
//        googleSignInClient.revokeAccess()

        connectToGoogleSignIn()

        binding.btnLoginViaGmail.setOnClickListener {
            intentToGoogleAccounts()
        }

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
        viewModel.loginResult.observe(this, Observer { response ->
            if (response != null) {
                LoginSharedPref.saveData(this, response)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage != null) {
                Utilities.customDialog(errorMessage, this)
            }
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