package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties
data class Coupon(
    val couponDescription: String,
    val couponName: String,
    val expiryDate: String,
    val generatedCouponCode: Int,
    val id: String,
    val value: Number)
