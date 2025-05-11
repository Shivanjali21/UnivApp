package com.practice.univapp.network.data

import com.google.gson.annotations.SerializedName

data class UniversityItem(
    @SerializedName("alpha_two_code")
    val alphaTwoCode: String,
    val country: String ? = null,
    val domains: List<String>,
    val name: String,
    @SerializedName("state-province")
    val stateProvince: String ? = null,
    @SerializedName("web_pages")
    val webPages: List<String>
)