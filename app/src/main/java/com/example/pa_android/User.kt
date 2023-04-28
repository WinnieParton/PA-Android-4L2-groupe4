package com.example.pa_android

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: String,
    val status: String
) : Parcelable