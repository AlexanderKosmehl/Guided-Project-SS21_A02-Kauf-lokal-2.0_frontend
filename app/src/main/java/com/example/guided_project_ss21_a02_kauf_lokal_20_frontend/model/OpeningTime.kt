package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model

import android.os.Parcelable
@kotlinx.parcelize.Parcelize
data class OpeningTime(
    var monday: String,
    var tuesday: String,
    var wednesday: String,
    var thursday: String,
    var friday: String,
    var saturday: String,
    var sunday: String,
    var isOpen: Boolean
): Parcelable

