package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model

// TODO: Backend
data class Coupon(
    override var id:String,
    override var timestamp: String,
    val couponDescription: String,
    val couponName: String,
    val expiryDate: String,
    val generatedCouponCode: Int,
    val value: Int,
    val eventType:String="coupon"
) : Event
