package com.skincarean.android.core.data

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.skincarean.android.core.data.source.remote.response.LoginUserResponse
import com.skincarean.android.ui.login.LoginActivity
import com.skincarean.android.ui.main.MainActivity

object LoginSharedPref {

    private fun sharedPreferences(activity: Activity): SharedPreferences {
        return activity.getSharedPreferences("login", Context.MODE_PRIVATE)
    }

    fun getToken(activity: Activity) =
        sharedPreferences(activity = activity).getString("token", null)

    fun getTokenCreatedAt(activity: Activity) =
        sharedPreferences(activity).getLong("token_created_at", 0)

    fun getTokenExpiredAt(activity: Activity) =
        sharedPreferences(activity).getLong("token_expired_at", 0)

    fun saveData(activity: Activity, loginUserResponse: LoginUserResponse) {
        val token = loginUserResponse.token
        val tokenCreatedAt = loginUserResponse.tokenCreatedAt
        val tokenExpiredAt = loginUserResponse.tokenExpiredAt
        sharedPreferences(activity)
            .edit()
            .putString("token", token)
            .putLong("token_created_at", tokenCreatedAt)
            .putLong("token_expired_at", tokenExpiredAt)
            .apply()
    }

    fun logout(activity: Activity) {
        sharedPreferences(activity)
            .edit()
            .clear()
            .apply()
        val intent = Intent(activity, LoginActivity::class.java)
        activity.startActivity(intent)
    }

    fun checkSession(activity: Activity) {
        when (activity) {
            is LoginActivity -> {
                if (getToken(activity) != null && getTokenExpiredAt(activity) > System.currentTimeMillis()) {
                    val intent = Intent(activity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    activity.startActivity(intent)
                }
            }

            else -> {
                if (getToken(activity) == null || getTokenExpiredAt(activity) < System.currentTimeMillis()) {
                    val intent = Intent(activity, LoginActivity::class.java)
                    activity.finishAffinity()
                    activity.startActivity(intent)
                }
            }
        }
    }

}