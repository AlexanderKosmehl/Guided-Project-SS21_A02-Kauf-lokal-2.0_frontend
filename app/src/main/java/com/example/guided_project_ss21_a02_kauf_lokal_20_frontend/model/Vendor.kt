package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model

import java.util.*

// TODO Re-check nullables
data class Vendor(
    var id: UUID,
    var name: String,
    var offerAmount: Int,
    var vendorScore: Double,
    var logo: String,
    var websiteURL: String,
    var color: String,
    var category: VendorCategory,
    var address: Address,
    var emailAddress: String,
    var company: String,
    var openingTime: OpeningTime,
    var detailImages: Set<String>,
    // var coupons: Set<Coupon>,   // TODO Fix coupon once they are finished
    var ratings: Set<Rating>,
    var products: Set<Product>,
    // var messages: Set<Message>, // TODO Fix messages once they are finished
    // var events: Set<Event>      // TODO Fix events once they are finished
)
