package com.lapperapp.laper.Data

data class FilterModel(
    val field: String,
    val value: String,
    val sort: Int = 1,
    val lim: Int = 0
)