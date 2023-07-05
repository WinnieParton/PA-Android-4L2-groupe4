package com.example.pa_android

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chat(
    val senderUser: Int,
    val receiverUser: Int,
    val message: String,
    val senderName: String,
    val receiverName: String,
    val name: String,
    val status: String,
    val currentDate: String,
    val send: Boolean
) : Parcelable