package com.lapperapp.laper.Data

data class ExpertModel(
    val _id:Object,
    val skills:ArrayList<String>,
    val date_created: Double,
    val email: String,
    val username: String,
    val name: String,
    val userImageUrl: String,
    val req:String,
    val lastActive: Double,
    val desc: String,
    val phoneNumber: String,
    val userType: String,
    val versionCode: Int?,
    val versionName: String?,
    val __v: Int
)
