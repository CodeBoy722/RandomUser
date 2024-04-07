package com.codeboy.randomuserandroid.views.detailUser

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.codeboy.randomuserandroid.R
import com.codeboy.randomuserandroid.utils.DataState
import com.codeboy.randomuserandroid.views.components.parseDateString

@Composable
fun DetailUser(
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry,
    viewModel: DetailUserViewModel = hiltViewModel()) {

    LaunchedEffect(key1 = "DetailRepasScreenKey") {
        val userObject = navBackStackEntry.arguments?.getString("user") ?: ""
        if (userObject.isNotBlank()) {
            viewModel.onEven(DetailUserEvents.GetUser(user = userObject))
        }
    }

    val uiState by viewModel.userScreenState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ){

       Row(modifier = Modifier
           .padding(horizontal = 16.dp)
           .height(64.dp)
           .fillMaxWidth(),
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.Start
       ) {

           Icon(modifier = Modifier
               .size(16.dp)
               .clickable {
                   navController.popBackStack()
               }
               .clip(CircleShape),
               imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_left),
               contentDescription = "Logo Arrow back",
               tint = Color.DarkGray
           )
           
           Text(modifier = Modifier.padding(start = 80.dp),
               text = stringResource(id = R.string.user_details),
               fontSize = 16.sp,
               fontWeight = FontWeight.Medium,
               lineHeight = 20.sp,
               textAlign = TextAlign.Start
           )
       }

        if (uiState.user is DataState.Success){

            val userData = uiState.user.extractData!!

            Row(modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(120.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Image(
                    painter = rememberAsyncImagePainter(userData.picture.large),
                    contentDescription = "user picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clip(RoundedCornerShape(50.dp)),
                )

                Column(modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(0.6f),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.Start
                ) {

                    Text(modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                        text = userData.name.first+" "+userData.name.last,
                        style = TextStyle(
                            color = colorResource(id = R.color.usernameColor),
                            fontSize = 16.sp,
                            fontWeight = FontWeight(600),
                            textAlign = TextAlign.Start,
                            lineHeight = 19.36.sp
                        )
                    )

                    Text(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                        text = userData.email,
                        style = TextStyle(
                            color = colorResource(id = R.color.userMailColor),
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400),
                            textAlign = TextAlign.Start,
                            lineHeight = 19.36.sp
                        )
                    )

                    Text(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                        text = ("Gender : " + userData.gender),
                        style = TextStyle(
                            color = colorResource(id = R.color.userMailColor),
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400),
                            textAlign = TextAlign.Start,
                            lineHeight = 19.36.sp
                        )
                    )

                    Text(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                        text = ("Country : " + userData.location.country),
                        style = TextStyle(
                            color = colorResource(id = R.color.userMailColor),
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400),
                            textAlign = TextAlign.Start,
                            lineHeight = 19.36.sp
                        )
                    )

                }

            }

            HorizontalDivider(modifier = Modifier.padding(16.dp), thickness = 1.2.dp, color = Color.LightGray)

            Row(modifier = Modifier.padding(16.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Age :",
                    style = TextStyle(
                        color = colorResource(id = R.color.userMailColor),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        lineHeight = 19.36.sp
                    )
                )

                Text(text = userData.dob.age.toString(),
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        lineHeight = 19.36.sp
                    )
                )
            }

            Row(modifier = Modifier.padding(16.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "State :",
                    style = TextStyle(
                        color = colorResource(id = R.color.userMailColor),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        lineHeight = 19.36.sp
                    )
                )

                Text(text = userData.location.state,
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        lineHeight = 19.36.sp
                    )
                )
            }

            Row(modifier = Modifier.padding(16.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Street :",
                    style = TextStyle(
                        color = colorResource(id = R.color.userMailColor),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        lineHeight = 19.36.sp
                    )
                )

                Text(text = userData.location.street.name,
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        lineHeight = 19.36.sp
                    )
                )
            }

            Row(modifier = Modifier.padding(16.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Postcode :",
                    style = TextStyle(
                        color = colorResource(id = R.color.userMailColor),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        lineHeight = 19.36.sp
                    )
                )

                Text(text = userData.location.postcode ?: "",
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        lineHeight = 19.36.sp
                    )
                )
            }

            Row(modifier = Modifier.padding(16.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Phone Number :",
                    style = TextStyle(
                        color = colorResource(id = R.color.userMailColor),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        lineHeight = 19.36.sp
                    )
                )

                Text(text = userData.phone,
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        lineHeight = 19.36.sp
                    )
                )
            }

            Row(modifier = Modifier.padding(16.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Registered date :",
                    style = TextStyle(
                        color = colorResource(id = R.color.userMailColor),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        lineHeight = 19.36.sp
                    )
                )

                Text(text = parseDateString(userData.registered.date),
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        lineHeight = 19.36.sp
                    )
                )
            }

            Row(modifier = Modifier.padding(16.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Birthday :",
                    style = TextStyle(
                        color = colorResource(id = R.color.userMailColor),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        lineHeight = 19.36.sp
                    )
                )

                Text(text = parseDateString(userData.dob.date),
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        lineHeight = 19.36.sp
                    )
                )
            }
        }
    }

}