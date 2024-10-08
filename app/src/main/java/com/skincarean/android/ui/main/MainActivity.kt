package com.skincarean.android.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.skincarean.android.R
import com.skincarean.android.ui.DetailActivity

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var btnLoginWithGoogle: Button
    private lateinit var mGoogleApiClient: GoogleApiClient

    companion object {
        const val RC_SIGN_IN = 123;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLoginWithGoogle = findViewById(R.id.btn_login_via_google)
        mAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()
        mGoogleApiClient = GoogleApiClient.Builder(this@MainActivity).enableAutoManage(
            this@MainActivity,
            object : GoogleApiClient.OnConnectionFailedListener {
                override fun onConnectionFailed(p0: ConnectionResult) {
                    Toast.makeText(
                        this@MainActivity,
                        "Koneksi dengan akun google  gagal",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build()

        btnLoginWithGoogle.setOnClickListener {
            loginGoogleAccount()
           startActivity( Intent(this, DetailActivity::class.java))
        }

    }

    private fun loginGoogleAccount() {
        val intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {

            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
            if (result!!.isSuccess) {
                val account = result.signInAccount
                if (account != null) {
                    sendTokenToBackend(account.idToken)
                    Log.d("idToken",account.idToken.toString())
                }

            }


        }

    }

    private fun sendTokenToBackend(idToken: String?) {


    }
}