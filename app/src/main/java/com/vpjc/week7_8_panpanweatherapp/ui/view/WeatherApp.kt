package com.vpjc.week7_8_panpanweatherapp.ui.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.vpjc.week7_8_panpanweatherapp.R
import com.vpjc.week7_8_panpanweatherapp.ui.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun PanPanWeatherApp(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = viewModel()
) {
    val weatherState by viewModel.weather.collectAsState()
    var userInputCityName by rememberSaveable { mutableStateOf("") }
    val iconByUrl by viewModel.weatherIconUrl.collectAsState()

    val currentDate by viewModel.currentDate.collectAsState()
    val currentTime by viewModel.currentTime.collectAsState()
    val listWeatherInfo by viewModel.listWeatherInfo.collectAsState()
    val listSunInfo by viewModel.listSunInfo.collectAsState()

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(R.drawable.weather___home_2),
                contentScale = ContentScale.Crop
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 8.dp, bottom = 22.dp)
                .padding(WindowInsets.statusBars.asPaddingValues()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                value = userInputCityName,
                onValueChange = { userInputCityName = it },
                placeholder = {
                    Text(
                        "Enter city name...",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search icon",
                        tint = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.size(16.dp)
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White.copy(alpha = 0.12f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.12f),
                    focusedBorderColor = Color.White.copy(alpha = 0.25f),
                    unfocusedBorderColor = Color.White.copy(alpha = 0.25f),
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .weight(0.4f),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (userInputCityName.isNotBlank()) {
                            viewModel.loadWeather(userInputCityName)
                            coroutineScope.launch {
                                listState.scrollToItem(0)
                            }
                            keyboardController?.hide()
                        }
                    }
                )
            )

            Spacer(modifier = modifier.width(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(0.15f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White.copy(alpha = 0.12f))
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.25f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable {
                        if (userInputCityName.isNotBlank()) {
                            viewModel.loadWeather(userInputCityName)
                            coroutineScope.launch {
                                listState.scrollToItem(0)
                            }
                            keyboardController?.hide()
                        }
                    }
                    .padding(horizontal = 10.dp, vertical = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search icon",
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    "Search",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState
        ) {
            item {
                if (weatherState.errorMessage != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ErrorView(weatherState.errorMessage)
                    }
                } else if (weatherState.cityName.isBlank()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 100.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = Color(0xFFA3A7B9),
                            modifier = Modifier.size(56.dp)
                        )
                        Text(
                            "Search for a city to get started",
                            color = Color(0xFFA3A7B9)
                        )
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(88.dp),
                        modifier = modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(
                                    6.dp,
                                    alignment = Alignment.CenterHorizontally
                                ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.LocationOn,
                                    contentDescription = "Location",
                                    tint = Color.White
                                )
                                Text(
                                    weatherState.cityName,
                                    color = Color.White,
                                    fontSize = 18.sp
                                )
                            }
                            Column(
                                verticalArrangement = Arrangement.spacedBy(
                                    4.dp,
                                    alignment = Alignment.CenterVertically
                                ),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    currentDate,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 36.sp
                                )
                                Text(
                                    "Updated as of $currentTime",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontSize = 16.sp
                                )
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = modifier
                                .fillMaxWidth()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(
                                    4.dp,
                                    alignment = Alignment.CenterVertically
                                )
                            ) {
                                iconByUrl?.let {
                                    Image(
                                        painter = rememberAsyncImagePainter(it),
                                        contentDescription = "Weather Icon",
                                        modifier = Modifier.size(64.dp)
                                    )
                                }
                                Text(
                                    weatherState.weatherCondition,
                                    color = Color.White,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "${weatherState.temperature.roundToInt()}°C",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 64.sp
                                )
                            }

                            Image(
                                painter = painterResource(
                                    when (weatherState.weatherCondition.lowercase()) {
                                        "clear" -> R.drawable.blue_and_black_bold_typography_quote_poster_3
                                        "rain" -> R.drawable.blue_and_black_bold_typography_quote_poster_2
                                        else -> R.drawable.blue_and_black_bold_typography_quote_poster
                                    }
                                ),
                                contentDescription = "Weather Character",
                                modifier = modifier
                                    .size(150.dp)
                            )
                        }

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = modifier
                                .fillMaxWidth()
                                .height(((listWeatherInfo.size + 3 - 1) / 3 * 130).dp),
                        ) {
                            items(listWeatherInfo) {
                                infoCardView(
                                    title = it.first,
                                    value = it.second,
                                    iconRes = it.third
                                )
                            }
                        }
                    }

                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        listSunInfo.forEach {
                            SunCardView(
                                title = it.first,
                                value = it.second,
                                iconRes = it.third
                            )
                        }
                    }
                }
                Spacer(modifier = modifier.height(24.dp))
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun PanPanWeatherAppPreview() {
    PanPanWeatherApp()
}