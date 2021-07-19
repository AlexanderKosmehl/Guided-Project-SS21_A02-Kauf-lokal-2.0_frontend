package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model

import android.os.Parcelable
import java.util.*

// TODO
@kotlinx.parcelize.Parcelize
data class Poll(
    var id: UUID,
    var title: String,
    var authorName: String,
    var totalAmountVoters: Int,
    var authorProfilePicture: String,
    var options: List<VotingOption>,
): Parcelable
