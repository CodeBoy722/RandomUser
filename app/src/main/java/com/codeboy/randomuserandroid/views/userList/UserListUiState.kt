package com.codeboy.randomuserandroid.views.userList

import com.codeboy.randomuserandroid.domain.models.User
import com.codeboy.randomuserandroid.utils.DataState

data class UserListUiState(
    val userListState: DataState<List<User>?> = DataState.IDLE()
)


