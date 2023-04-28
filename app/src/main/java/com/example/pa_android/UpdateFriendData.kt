package com.example.pa_android

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpdateFriendData(
    val receiver: String,
    val status: String
) : Parcelable