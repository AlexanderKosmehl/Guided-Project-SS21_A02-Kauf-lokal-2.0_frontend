package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Event (
    var id: UUID,
    var eventTypes: EventTypes,
    var refId: UUID,
    var vendorId: UUID,
    var created: Date
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

