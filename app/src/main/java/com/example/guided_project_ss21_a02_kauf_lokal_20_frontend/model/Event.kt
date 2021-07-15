package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime
import java.util.*

@Parcelize
data class Event (
    var id: UUID,
    var eventTypes: EventTypes,
    var refId: UUID,
    var vendorId: UUID,
    var created: Date
): Parcelable
