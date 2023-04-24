package com.example.pa_android

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignupData(
    val name: String,
    val email: String,
    val password: String,
    val role: String
) : Parcelable