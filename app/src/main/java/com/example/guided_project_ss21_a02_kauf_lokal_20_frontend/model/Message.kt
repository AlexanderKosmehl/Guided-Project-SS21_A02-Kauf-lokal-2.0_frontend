package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

import java.util.*

@kotlinx.parcelize.Parcelize
data class Message(
    var id: UUID,
    var message: String,
    var created: Date
): Parcelable
