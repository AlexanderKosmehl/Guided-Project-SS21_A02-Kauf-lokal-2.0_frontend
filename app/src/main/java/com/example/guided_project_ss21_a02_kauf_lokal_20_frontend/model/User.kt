package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@kotlinx.parcelize.Parcelize
data class User(
    val address: Address,
    val email: String,
    val favoriteCouponIDs: List<String>, //TODO
    val favoriteVendorsIDs: List<String>, //TODO
    val firstName: String,
    val id: String,
    val lastName: String,
    val ratings: List<Rating>
): Parcelable {

/*    @kotlinx.parcelize.Parcelize
    data class FavoriteCoupon(
        val id: String
    ) : Parcelable*/

}


