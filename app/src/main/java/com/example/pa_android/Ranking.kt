package com.example.pa_android

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ranking(
    val appid: Int,
    val user: User?,
    val score: Int?
) : Parcelable