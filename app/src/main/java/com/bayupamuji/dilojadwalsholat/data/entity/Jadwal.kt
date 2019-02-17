package com.bayupamuji.dilojadwalsholat.data.entity

data class JadwalResponse(
    val items: List<Item>
)

data class Item(
    val asr: String,
    val date_for: String,
    val dhuhr: String,
    val fajr: String,
    val isha: String,
    val maghrib: String,
    val shurooq: String
)