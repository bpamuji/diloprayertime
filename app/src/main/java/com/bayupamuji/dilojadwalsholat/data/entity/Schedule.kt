package com.bayupamuji.dilojadwalsholat.data.entity

data class ScheduleResponse(
    val code:Int,
    val data: Data
)

data class MonthScheduleResponse(
    val code: Int,
    val data: List<Data>
)

data class Data(
    val date: Date,
    val meta: Meta,
    val timings: Timings
)

data class Date(
    val gregorian: Gregorian,
    val hijri: Hijri,
    val readable: String,
    val timestamp: String
)

data class Meta(
    val latitude: Double,
    val longitude: Double,
    val method: Method,
    val timezone: String
)

data class Timings(
    val Asr: String,
    val Dhuhr: String,
    val Fajr: String,
    val Imsak: String,
    val Isha: String,
    val Maghrib: String,
    val Midnight: String,
    val Sunrise: String,
    val Sunset: String
)

data class Method(
    val id: Int,
    val name: String
)

data class Hijri(
    val date: String,
    val day: String,
    val format: String,
    val month: Month,
    val weekday: Weekday,
    val year: String
)

data class Month(
    val ar: String,
    val en: String,
    val number: Int
)

data class Weekday(
    val ar: String,
    val en: String
)

data class Gregorian(
    val date: String,
    val day: String,
    val format: String,
    val month: MonthX,
    val weekday: WeekdayX,
    val year: String
)

data class WeekdayX(
    val en: String
)

data class MonthX(
    val en: String,
    val number: Int
)