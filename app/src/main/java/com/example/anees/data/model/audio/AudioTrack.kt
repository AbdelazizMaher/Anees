package com.example.anees.data.model.audio

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AudioTrack(
    val index: Int,
    val title: String,
    val description: String,
    val reciter: String,
    val reciterImage: String,
    val typeIcon: String,
    val reciterBaseUrl: String,
    val uri: String,
) : Parcelable


