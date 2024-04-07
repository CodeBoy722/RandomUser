package com.codeboy.randomuserandroid.views.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.codeboy.randomuserandroid.R
import com.codeboy.randomuserandroid.domain.models.User
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun UserItem(user: User, onUserClicked: () -> Unit) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(90.dp)
        .clickable {
            onUserClicked.invoke()
        },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = rememberAsyncImagePainter(user.picture.large),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                //.weight(0.2f)
                .size(60.dp)
                .clip(RoundedCornerShape(30.dp)),
        )

        Column(modifier = Modifier
            .padding(start = 8.dp)
            .weight(0.9f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
                text = user.name.first+" "+user.name.last,
                style = TextStyle(
                    color = colorResource(id = R.color.usernameColor),
                    fontSize = 14.sp,
                    fontWeight = FontWeight(600),
                    textAlign = TextAlign.Start,
                    lineHeight = 19.36.sp
                )
            )

            Text(modifier = Modifier.fillMaxWidth(),
                text = user.email,
                style = TextStyle(
                    color = colorResource(id = R.color.userMailColor),
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    textAlign = TextAlign.Start,
                    lineHeight = 19.36.sp
                )
            )
        }

        Icon(modifier = Modifier
            .size(28.dp)
            .weight(0.1f),
            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_right),
            tint = colorResource(id = R.color.purple_200),
            contentDescription = "go to user detail"
        )

    }
}

@Composable
fun ShimmerLoader(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.align(alignment = Alignment.TopCenter)
        ) {

            Row {
                ComponentCircle()
                Spacer(modifier = Modifier.padding(4.dp))
                Column {
                    Spacer(modifier = Modifier.padding(8.dp))
                    ComponentRectangleLineLong()
                    Spacer(modifier = Modifier.padding(4.dp))
                    ComponentRectangleLineShort()
                }
            }
            Spacer(modifier = Modifier.padding(24.dp))

            Row {
                ComponentCircle()
                Spacer(modifier = Modifier.padding(4.dp))
                Column {
                    Spacer(modifier = Modifier.padding(8.dp))
                    ComponentRectangleLineLong()
                    Spacer(modifier = Modifier.padding(4.dp))
                    ComponentRectangleLineShort()
                }
            }
            Spacer(modifier = Modifier.padding(24.dp))

            Row {
                ComponentCircle()
                Spacer(modifier = Modifier.padding(4.dp))
                Column {
                    Spacer(modifier = Modifier.padding(8.dp))
                    ComponentRectangleLineLong()
                    Spacer(modifier = Modifier.padding(4.dp))
                    ComponentRectangleLineShort()
                }
            }
            Spacer(modifier = Modifier.padding(24.dp))

            Row {
                ComponentCircle()
                Spacer(modifier = Modifier.padding(4.dp))
                Column {
                    Spacer(modifier = Modifier.padding(8.dp))
                    ComponentRectangleLineLong()
                    Spacer(modifier = Modifier.padding(4.dp))
                    ComponentRectangleLineShort()
                }
            }
            Spacer(modifier = Modifier.padding(24.dp))

            Row {
                ComponentCircle()
                Spacer(modifier = Modifier.padding(4.dp))
                Column {
                    Spacer(modifier = Modifier.padding(8.dp))
                    ComponentRectangleLineLong()
                    Spacer(modifier = Modifier.padding(4.dp))
                    ComponentRectangleLineShort()
                }
            }
            Spacer(modifier = Modifier.padding(24.dp))

        }
    }

}

@Composable
fun ComponentCircle() {
    Box(
        modifier = Modifier
            .background(color = Color.LightGray, shape = CircleShape)
            .size(80.dp)
            .shimmerLoadingAnimation()
    )
}

@Composable
fun ComponentRectangleLineLong() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .size(height = 20.dp, width = 200.dp)
            .shimmerLoadingAnimation()
    )
}

@Composable
fun ComponentRectangleLineShort() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .size(height = 20.dp, width = 100.dp)
            .shimmerLoadingAnimation()
    )
}

fun Modifier.shimmerLoadingAnimation(
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
): Modifier {
    return composed {

        val shimmerColors = listOf(
            Color.White.copy(alpha = 0.3f),
            Color.White.copy(alpha = 0.5f),
            Color.White.copy(alpha = 1.0f),
            Color.White.copy(alpha = 0.5f),
            Color.White.copy(alpha = 0.3f),
        )

        val transition = rememberInfiniteTransition(label = "")

        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart,
            ),
            label = "Shimmer loading animation",
        )

        this.background(
            brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                end = Offset(x = translateAnimation.value, y = angleOfAxisY),
            ),
        )
    }
}

fun parseDateString(dateString: String) : String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val parsedDate = dateFormat.parse(dateString)

    val outputDateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale.getDefault())
    return outputDateFormat.format(parsedDate ?: Date())
}

fun LazyListState.isScrolledToEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1