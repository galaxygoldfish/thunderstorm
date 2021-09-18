package com.thunderstorm.app.android.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieDrawable
import com.airbnb.lottie.compose.LottieAnimatable
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.viewmodel.WeatherViewModel

@Composable
fun WeatherView(
    viewModel: WeatherViewModel,
    navController: NavController
) {
    viewModel.loadDefaultCity(navController.context)
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = viewModel.currentCityName.value.toString(),
                modifier = Modifier.padding(top = 20.dp, start = 20.dp),
                style = MaterialTheme.typography.h3
            )
            Text(
                text = viewModel.currentRegionName.value.toString(),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(top = 1.dp, start = 20.dp),
                fontSize = 14.sp
            )
            if (viewModel.currentWeatherData.value != null) {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp)
                    ) {
                        Column(
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Text(
                                text = viewModel.currentWeatherData.value!!.current.tempFarenheit.toInt()
                                    .toString() + "°",
                                style = MaterialTheme.typography.h1,
                                modifier = Modifier
                                    .padding(start = 20.dp)
                            )
                            Text(
                                text = viewModel.currentWeatherData.value!!.current.condition.text,
                                modifier = Modifier.padding(start = 20.dp),
                                style = MaterialTheme.typography.subtitle1
                            )
                        }
                        Image(
                            painter = painterResource(id = viewModel.currentIconResource.value),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 25.dp)
                                .size(120.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            } else {
                LottieAnimation(
                    rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading_anim)).value,
                    modifier = Modifier
                        .fillMaxHeight()
                        .size(100.dp)
                        .align(Alignment.CenterHorizontally).size(100.dp)
                )
            }
        }
    }
}