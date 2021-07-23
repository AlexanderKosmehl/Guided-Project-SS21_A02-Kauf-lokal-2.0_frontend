package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

// TODO: Backend
@Parcelize
data class Coupon(
    var id: UUID,
    var name: String,
    var description: String,
    var couponCode: Int,
    var expiryDate: Date,
    var value: Float,
    var created: Date
): Parcelable
