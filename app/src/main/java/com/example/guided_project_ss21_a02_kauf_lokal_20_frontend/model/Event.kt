package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model

import java.time.LocalDateTime
import java.util.*

data class Event (
    var id: UUID,
    var eventTypes: EventTypes,
    var refId: UUID,
    var vendorId: UUID,
    var created: String
)
