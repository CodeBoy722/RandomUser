package com.codeboy.randomuserandroid.views.userList

sealed class UserListEvents {
    data object GetRandomUsers : UserListEvents()
}