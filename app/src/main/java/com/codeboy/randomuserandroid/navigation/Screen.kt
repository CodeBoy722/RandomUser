package com.codeboy.randomuserandroid.navigation

sealed class Screen(val route: String) {
    object UserListPage : Screen("UserList")
    object UserDetailPage : Screen("DetailUser/?user={user}")
}