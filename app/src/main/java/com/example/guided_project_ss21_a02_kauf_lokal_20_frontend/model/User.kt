package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@kotlinx.parcelize.Parcelize
data class User(
    val id: String,
    val level: Int,
    val experience: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val address: Address,
    val ratings: List<Rating>,
    val favoriteVendorsIDs: List<String>, //TODO
    val favoriteCouponIDs: List<String>, //TODO
    val experiences: List<String>, //TODO
): Parcelable {

/*    @kotlinx.parcelize.Parcelize
    data class FavoriteCoupon(
        val id: String
    ) : Parcelable*/

}


