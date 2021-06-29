package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.Model

import java.util.*

data class Vendor(
    var id: UUID,
    var name: String,
    var company: String,
    var merchantScore: Int,
    var address: Address,
    var profilePicture: String,
    var emailAddress: String,
    var openingTime: OpeningTime,
    // TODO: Additional fields should be changed in backend
)
