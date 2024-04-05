package com.lapperapp.laper.ui.NewDashboard.NewRequest

import com.lapperapp.laper.ui.NewHome.SelectCategorymodel

data class NewRequestSentModel(
    val reqSentDate: Long,
    val expertId: String,
    val reqId: String,
    val expName: String,
    val expImage: String,
    val ps: String,
    val requiredTech: Array<SelectCategorymodel>
)
