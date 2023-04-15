package com.example.pa_android

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chat(
    val appid: Int,
    val header_image: String?,
    val name: String?,
    val detailed_description: String?
) : Parcelable