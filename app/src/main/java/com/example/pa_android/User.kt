package com.example.pa_android

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val email: String?,
    val username: String?,
    val uid: Int,
    val password: String?,
    val image: String?,
    val status: String?
) : Parcelable