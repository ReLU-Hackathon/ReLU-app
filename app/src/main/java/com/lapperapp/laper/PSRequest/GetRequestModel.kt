package com.lapperapp.laper.PSRequest

import com.lapperapp.laper.ui.NewHome.SelectCategorymodel

data class GetRequestModel(val _id:String,
                           val requiredTech:ArrayList<SelectCategorymodel>,
                           val requestTime:String,
                           val accepted:Boolean,
                           val clientId:String,
                           val expertId:String,
                           val problemSolved:Boolean,
                           val requestId:String,
                           val problemStatement:String,
                           val __v:Int
)