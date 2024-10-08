package com.skincarean.android.core.data.source.remote.response

data class UserData(
    val userId: String, // ID pengguna di sistem backend
    val email: String, // Alamat email pengguna
    val displayName: String?, // Nama pengguna (bisa null)
    val profilePictureUrl: String? // URL foto profil pengguna (bisa null)
)
