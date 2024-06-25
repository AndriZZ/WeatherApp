package com.andriivanov.weatherapp.ui.weather.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.andriivanov.weatherapp.R
import com.andriivanov.weatherapp.ui.theme.WeatherTheme
import com.andriivanov.weatherapp.utils.WeatherUtil

@Composable
fun ForecastComponent(
    modifier: Modifier = Modifier,
    date: String,
    weatherCode: Int,
    minTemp: String,
    maxTemp: String,
) {
    ElevatedCard(
        modifier = modifier.padding(end = 16.dp),
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
                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                text = date.toString(),
                style = MaterialTheme.typography.titleMedium
            )
            AsyncImage(
                modifier = Modifier.size(42.dp),
                model = WeatherUtil.getWeatherIcon(weatherCode),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_placeholder),
                placeholder = painterResource(id = R.drawable.ic_placeholder),
            )
            Text(
                text = maxTemp,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.width(4.dp))

            Text(
                text = minTemp, style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ForecastComponentPreview() {
    Surface {
        WeatherTheme {
            ForecastComponent(
                date = "2023-10-07",
                weatherCode = 0,
                minTemp = "12",
                maxTemp = "28",
            )
        }
    }
}