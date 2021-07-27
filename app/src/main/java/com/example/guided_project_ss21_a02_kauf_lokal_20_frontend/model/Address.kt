package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class Address(
    var street: String,
    var houseNr: String,
    var place: String,
    var zipCode: String,
    var country: String
): Parcelable
