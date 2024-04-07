package com.codeboy.randomuserandroid.views.userList

import com.codeboy.randomuserandroid.domain.models.User

sealed class UserListEvents {
    data object GetRandomUsers : UserListEvents()
    data class GetSavedUsers(val savedUsers: List<User>) : UserListEvents()

    data object GetNextUsers : UserListEvents()

    data object RefreshUsers : UserListEvents()
}