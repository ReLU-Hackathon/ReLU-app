package com.laperapp.laper.Data

import java.util.*

data class UserModel(
    val _id:Object,
    val date_created: Date,
    val email: String,
    val username: String,
    val name: String,
    val userImageUrl: String,
    val lastActive: Date,
    val desc: String,
    val phoneNumber: String,
    val userType: String,
    val versionCode: Int?,
    val versionName: String?,
    val __v: Int
)
