package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat

import java.util.*

@kotlinx.parcelize.Parcelize
data class Message(
    var id: UUID,
    var message: String,
    var created: Date,
    var imageURL: String,
    var title: String,
    var vendorId: String,
): Parcelable {

    fun formatDate(): String {
        var formattedDate: String
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        formattedDate = simpleDateFormat.format(this.created)

        return formattedDate
    }

    fun formatTime(): String {
        var formattedTime: String
        val simpleDateFormat = SimpleDateFormat("HH:mm")
        formattedTime = simpleDateFormat.format(this.created)

        return formatTime()
    }
}
