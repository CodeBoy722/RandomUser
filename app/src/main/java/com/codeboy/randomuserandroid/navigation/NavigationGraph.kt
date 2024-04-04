package com.codeboy.randomuserandroid.navigation
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.codeboy.randomuserandroid.views.detailUser.DetailUser
import com.codeboy.randomuserandroid.views.userList.UserList

@Composable
fun SetupNavigation(navController: NavHostController) {

    // from android api 34 splash screen is no longer needed
    val startDestination = if (Build.VERSION.SDK_INT >= 34) {
        Screen.UserListPage.route
    } else {
        Screen.UserListPage.route
    }

    NavHost(navController = navController, startDestination = startDestination) {

        composable(route = Screen.UserListPage.route) {
            UserList(navController = navController)
        }

        composable(route = Screen.UserDetailPage.route, arguments = listOf(
            navArgument(
                name = "user"
            ) {
                type = NavType.StringType
                defaultValue = ""
            }
        )) {  navBackStackEntry ->
            DetailUser(navController = navController, navBackStackEntry)
        }

    }

}