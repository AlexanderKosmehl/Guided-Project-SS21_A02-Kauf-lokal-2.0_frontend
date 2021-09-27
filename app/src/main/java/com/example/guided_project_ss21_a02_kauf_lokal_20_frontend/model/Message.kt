package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model

import android.os.Parcelable
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
) : Parcelable {
    fun formatDate(): String = SimpleDateFormat("dd.MM.yyyy").format(this.created)
    fun formatTime(): String = SimpleDateFormat("HH:mm").format(this.created)

}
