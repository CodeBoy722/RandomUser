package com.codeboy.randomuserandroid.views.detailUser

import com.codeboy.randomuserandroid.domain.models.User
import com.codeboy.randomuserandroid.utils.DataState

data class DetailUserState (
    val user: DataState<User> = DataState.IDLE()
)