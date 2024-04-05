package com.lapperapp.laper.PSRequest

import com.lapperapp.laper.ui.NewHome.SelectCategorymodel

data class RequestModel(val problemStatement:String, val requiredTech:ArrayList<SelectCategorymodel>,val imageUrls:ArrayList<String>)