package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model

import android.os.Parcelable
import java.util.*
@kotlinx.parcelize.Parcelize
data class Product(
    var id: UUID,
    var name: String,
    var description: String,
    var price: Double
): Parcelable

