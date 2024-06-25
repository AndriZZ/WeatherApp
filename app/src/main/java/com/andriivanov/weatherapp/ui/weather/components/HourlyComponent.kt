package com.andriivanov.weatherapp.ui.weather.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.andriivanov.weatherapp.R
import com.andriivanov.weatherapp.ui.theme.WeatherTheme
import com.andriivanov.weatherapp.utils.WeatherUtil

@Composable
fun HourlyComponent(
    modifier: Modifier = Modifier,
    time: String,
    weatherCode: Int,
    temperature: String,
) {
    ElevatedCard(
        modifier = modifier.padding(end = 12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = time.substring(11, 13),
                style = MaterialTheme.typography.titleMedium,
            )
            AsyncImage(
                modifier = Modifier.size(42.dp),
                model = WeatherUtil.getWeatherIcon(weatherCode),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.sunny),
                placeholder = painterResource(id = R.drawable.sunny),

                )
            Text(
                text = temperature,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HourlyComponentPreview() {
    Surface {
        WeatherTheme {
            HourlyComponent(
                time = "2024-06-07 13:00",
                weatherCode = 0,
                temperature = "23",
            )
        }
    }
}