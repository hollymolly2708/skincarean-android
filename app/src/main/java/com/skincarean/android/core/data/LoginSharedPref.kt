package com.skincarean.android.core.data

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.skincarean.android.core.data.domain.model.user.LoginUser
import com.skincarean.android.ui.login.LoginActivity
import com.skincarean.android.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object LoginSharedPref {

    private fun sharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("login", Context.MODE_PRIVATE)
    }

    fun getToken(context: Context) =
        sharedPreferences(context.applicationContext).getString("token", null)

    fun getTokenCreatedAt(context: Context) =
        sharedPreferences(context).getLong("token_created_at", 0)

    fun getTokenExpiredAt(context: Context) =
        sharedPreferences(context).getLong("token_expired_at", 0)

    fun saveData(context: Context, loginUserResponse: LoginUser) {
        val token = loginUserResponse.token
        val tokenCreatedAt = loginUserResponse.tokenCreatedAt
        val tokenExpiredAt = loginUserResponse.tokenExpiredAt
        sharedPreferences(context).edit().putString("token", token)
            .putLong("token_created_at", tokenCreatedAt).putLong("token_expired_at", tokenExpiredAt)
            .apply()
    }

    fun clear(context: Context) {
        sharedPreferences(context).edit().clear().apply()

        CoroutineScope(Dispatchers.Main).launch {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }

    }

    fun checkSession(context: Context) {
        when (context) {
            is LoginActivity -> {
                if (getToken(context) != null && getTokenExpiredAt(context) > System.currentTimeMillis()) {
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                }
            }

            else -> {
                if (getToken(context) == null || getTokenExpiredAt(context) < System.currentTimeMillis()) {
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                }
            }
        }
    }

}