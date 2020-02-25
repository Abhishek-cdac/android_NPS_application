package com.nectar.nps.data


import com.google.gson.annotations.SerializedName

data class Dashboard1(
    @SerializedName("NetworkType")
    val networkType: String,
    @SerializedName("NetworkTypeID")
    val networkTypeID: Int,
    @SerializedName("TotalCount")
    val totalCount: Int,
    @SerializedName("VenderID")
    val venderID: Int,
    @SerializedName("VenderName")
    val venderName: String


)