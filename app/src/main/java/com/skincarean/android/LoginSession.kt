package com.skincarean.android

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.skincarean.android.ui.login.LoginActivity
import com.skincarean.android.ui.main.MainActivity
import com.skincarean.core.LoginSharedPreferences

object LoginSession {
    fun checkSession(context: Context) {
        when (context) {
            is LoginActivity -> {
                if (LoginSharedPreferences.getToken(context) != null && LoginSharedPreferences.getTokenExpiredAt(
                        context
                    ) > System.currentTimeMillis()
                ) {
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                }
            }

            else -> {
                if (LoginSharedPreferences.getToken(context) == null || LoginSharedPreferences.getTokenExpiredAt(
                        context
                    ) < System.currentTimeMillis()
                ) {
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                }
            }
        }
    }


    fun logout(activity : Activity) {
        if (LoginSharedPreferences.isLogout) {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            activity.startActivity(intent)
        }
    }
}