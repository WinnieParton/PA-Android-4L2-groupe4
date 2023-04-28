package com.example.pa_android

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Friend(
    val id: String,
    val name: String,
    val email: String,
    val role: String
) : Parcelable