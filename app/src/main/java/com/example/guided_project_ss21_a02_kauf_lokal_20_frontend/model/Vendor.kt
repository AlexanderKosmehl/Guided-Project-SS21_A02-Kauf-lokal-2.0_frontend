package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model

import java.util.*

data class Vendor(
    var id: UUID,
    var name: String,
    var offerAmount: Int,
    var vendorScore: Int,
    var logo: String,
    var websiteURL: String,
    var color: String,
    var category: VendorCategory,
    var address: Address,
    var emailAddress: String,
    var company: String,
    var openingTime: OpeningTime,
    var detailImages: Set<String?>,
    var coupons: Set<Coupon?>,
    var ratings: Set<Rating?>,
    var products: Set<Product?>,
    var messages: Set<Message?>,
    var events: Set<Event?>
)
