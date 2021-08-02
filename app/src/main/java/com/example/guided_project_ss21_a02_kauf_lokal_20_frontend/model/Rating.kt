package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class Rating(
    val id: String,
    val ratingScore: Float,
): Parcelable
