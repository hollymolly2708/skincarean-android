package com.skincarean.core

import android.content.Context
import android.content.SharedPreferences
import com.skincarean.android.core.data.domain.model.user.LoginUser

object LoginSharedPreferences {


    var isLogout: Boolean = false

    fun sharedPreferences(context: Context): SharedPreferences {
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
        isLogout = true
    }

}