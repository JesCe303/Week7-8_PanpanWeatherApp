package com.vpjc.week7_8_panpanweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.vpjc.week7_8_panpanweatherapp.ui.theme.Week78_PanpanWeatherAppTheme
import com.vpjc.week7_8_panpanweatherapp.ui.view.PanPanWeatherApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week78_PanpanWeatherAppTheme {
                PanPanWeatherApp()
            }
        }
    }
}
