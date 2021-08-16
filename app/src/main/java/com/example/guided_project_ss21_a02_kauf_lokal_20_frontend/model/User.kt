package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@kotlinx.parcelize.Parcelize
data class User(
    val id: UUID,
    val level: Int,
    val experience: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val address: Address,
    val ratings: List<Rating>,
    val favoriteVendorsIDs: List<Vendor>,
    val favoriteCouponIDs: List<Coupon>,
    val experiences: List<Experience>,
): Parcelable {

/*    @kotlinx.parcelize.Parcelize
    data class FavoriteCoupon(
        val id: String
    ) : Parcelable*/

}


