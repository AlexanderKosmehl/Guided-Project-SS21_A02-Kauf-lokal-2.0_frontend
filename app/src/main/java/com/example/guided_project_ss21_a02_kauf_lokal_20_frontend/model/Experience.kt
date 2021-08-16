package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model

import android.os.Parcelable
import java.util.*
@kotlinx.parcelize.Parcelize
data class Experience(
    val id: UUID,
    val vendor: Vendor,
    val user: User,
    val experience: Int,
    val description: String,
    val created: Date,
): Parcelable

