package com.skincarean.android.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.skincarean.android.R
import com.skincarean.android.ui.main.MainActivity

class DetailActivity : AppCompatActivity() {
    private lateinit var btnLogout: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        mAuth = FirebaseAuth.getInstance()

        btnLogout = findViewById(R.id.btn_logout)
        btnLogout.setOnClickListener {
            googleSignInClient.revokeAccess().addOnCompleteListener(this) {
                val intent = Intent(this@DetailActivity, MainActivity::class.java)
                startActivity(intent)
            }



        }
    }
}