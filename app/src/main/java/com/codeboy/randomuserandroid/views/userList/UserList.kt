package com.codeboy.randomuserandroid.views.userList

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.codeboy.randomuserandroid.R
import com.codeboy.randomuserandroid.navigation.Screen
import com.codeboy.randomuserandroid.ui.theme.Pink40
import com.codeboy.randomuserandroid.utils.DataState
import com.codeboy.randomuserandroid.utils.UserDataStoreUtil
import com.codeboy.randomuserandroid.views.components.ShimmerLoader
import com.codeboy.randomuserandroid.views.components.UserItem
import com.codeboy.randomuserandroid.views.components.isScrolledToEnd
import com.google.gson.Gson
import kotlinx.coroutines.launch


@Composable
fun UserList(
    navController: NavHostController,
    viewModel: UserListViewModel = hiltViewModel()) {

    val scope = rememberCoroutineScope()
    val uiState by viewModel.userListScreenState.collectAsState()
    val users = uiState.userListState.extractData

    var loader by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    // observer when reached end of list
    val endOfListReached by remember {
        derivedStateOf {
            listState.isScrolledToEnd()
        }
    }

    // call for next page of users
    LaunchedEffect(endOfListReached) {
        if(users != null){
            loader = true
            viewModel.onEven(UserListEvents.GetNextUsers)
        }
    }

    Column(modifier = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(modifier = Modifier
            .padding(top = 30.dp)
            .fillMaxWidth()
            .weight(0.1f),
            text = "RandomUser",
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = Pink40,
                lineHeight = 20.sp
            )
        )

        when(uiState.userListState){

            is DataState.Loading -> {
                // show shimmer loader
                ShimmerLoader()
            }

            is DataState.Success -> {
                /*Toast.makeText(LocalContext.current,
                    "state is success", Toast.LENGTH_LONG).show()*/

                // when getting next page set loader to false when data is here
                loader = false
                //show data
                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f),
                    state = listState,
                    userScrollEnabled = true) {
                    if (users != null) {
                        items(users.size) { i ->
                            Row {
                                UserItem(users[i], onUserClicked = {  ->
                                    navController.navigate(
                                        Screen.UserDetailPage.route.replace(
                                            oldValue = "{user}",
                                            newValue = Gson().toJson(users[i])
                                        )
                                    )
                                })
                            }
                        }
                    }
                }

                if (loader) {
                    CircularProgressIndicator(modifier = Modifier.weight(0.1f, true), color = colorResource(
                        id = R.color.purple_500
                    ))
                }
            }

            is DataState.Failure -> {

                val savedUsers = UserDataStoreUtil(LocalContext.current).getLastUserList()
                LaunchedEffect(endOfListReached) {
                    if (savedUsers.isNotEmpty()){
                        viewModel.onEven(UserListEvents.GetSavedUsers(savedUsers))
                    }
                }

                if(savedUsers.isEmpty()){
                    Column(modifier = Modifier.weight(0.9f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Column(modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth()
                            .height(180.dp)
                            .align(Alignment.Start),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally) {

                            Icon(modifier = Modifier
                                .size(80.dp)
                                .weight(0.1f),
                                painter = painterResource(R.drawable.ic_error),
                                tint = colorResource(id = R.color.purple_200),
                                contentDescription = stringResource(R.string.go_to_detail)
                            )

                            Text(modifier = Modifier.fillMaxWidth(),
                                text = stringResource(R.string.error_api),
                                style = TextStyle(
                                    color = colorResource(id = R.color.userMailColor),
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight(400),
                                    textAlign = TextAlign.Center,
                                    lineHeight = 19.36.sp
                                )
                            )

                            Box(modifier = Modifier
                                .height(60.dp)
                                .width(100.dp)
                                .padding(16.dp)
                                .clickable {
                                    scope.launch {
                                        viewModel.onEven(
                                            UserListEvents.GetRandomUsers
                                        )
                                    }
                                }
                                .background(Color.LightGray, RoundedCornerShape(16.dp)),
                                contentAlignment = Alignment.Center
                            ){

                                Text(modifier = Modifier,
                                    text = "Refresh",
                                    style = TextStyle(
                                        color = Color.White,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight(500),
                                        textAlign = TextAlign.Center,
                                    )
                                )
                            }
                        }
                    }
                }else{
                    Toast.makeText(LocalContext.current,
                        stringResource(R.string.your_internet_is_unstable), Toast.LENGTH_LONG).show()
                }
            }

            is DataState.IDLE -> {
                // nothing
            }
        }
    }
}




