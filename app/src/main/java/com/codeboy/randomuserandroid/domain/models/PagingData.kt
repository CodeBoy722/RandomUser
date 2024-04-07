package com.codeboy.randomuserandroid.domain.models

data class PagingData(
    var page: Int = 1,
    val results: Int = 20,
    val seed: String = "weenect"
)
