package com.skincarean.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

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
                if(account != null){
                    firebaseAuthWithGoogle(account)

                }

            }


        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, object : OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        val intent = Intent(this@MainActivity, DetailActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this@MainActivity, "Selamat datang ${user?.displayName}",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@MainActivity, "Authentication gagal",Toast.LENGTH_SHORT).show()
                    }
                }

            })
    }
}