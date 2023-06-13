package com.example.pa_android

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddFriendData(
    val sender: String
) : Parcelable