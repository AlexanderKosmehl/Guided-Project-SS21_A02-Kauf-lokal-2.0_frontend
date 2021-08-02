package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model

import android.os.Parcelable
import java.util.*

@kotlinx.parcelize.Parcelize
data class VotingOption(
    var id: UUID,
    var title: String,
    var totalAmountVoters: Int,
    var users: List<String>,
): Parcelable

