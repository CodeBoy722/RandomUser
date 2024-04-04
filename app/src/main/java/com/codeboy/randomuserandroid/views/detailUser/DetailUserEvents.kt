package com.codeboy.randomuserandroid.views.detailUser

sealed class DetailUserEvents {
    data class GetUser(val user: String) : DetailUserEvents()
}