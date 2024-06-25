package com.andriivanov.weatherapp.ui.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.andriivanov.weatherapp.R
import com.andriivanov.weatherapp.data.model.DailyForecast
import com.andriivanov.weatherapp.ui.mocks.MockedWeatherScreen
import com.andriivanov.weatherapp.ui.mocks.MockedWeatherScreen.mockWeatherViewModel
import com.andriivanov.weatherapp.ui.weather.components.ForecastComponent
import com.andriivanov.weatherapp.ui.weather.components.HourlyComponent
import com.andriivanov.weatherapp.ui.weather.components.WeatherComponent
import com.andriivanov.weatherapp.utils.DateUtil.toFormattedDate
import com.andriivanov.weatherapp.utils.DateUtil.toFormattedDay
import com.andriivanov.weatherapp.utils.Destinations
import com.andriivanov.weatherapp.utils.PreferenceHelper
import com.andriivanov.weatherapp.utils.PreferencesHelper.Companion.SELECTED_TAB_INDEX
import com.andriivanov.weatherapp.utils.WeatherUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = hiltViewModel(),
    preferencesHelper: PreferenceHelper
) {
    val uiState: WeatherUiState by viewModel.uiState.collectAsStateWithLifecycle()
    // State for scaffold and dropdown
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Sofia", "Burgas", "Plovdiv")
    val savedSelectedIndex = preferencesHelper.getInt(SELECTED_TAB_INDEX, 0)
    var selectedIndex by remember { mutableIntStateOf(savedSelectedIndex) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                actions = {
                    Box(
                        Modifier
                            .padding(end = 12.dp)
                            .fillMaxSize()
                    ) {
                        TextButton(
                            onClick = { expanded = !expanded },
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(items[selectedIndex])
                            Icon(
                                Icons.Filled.ArrowDropDown,
                                contentDescription = stringResource(R.string.dropdown)
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items.forEachIndexed { index, item ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedIndex = index
                                        preferencesHelper.saveInt(SELECTED_TAB_INDEX, index)
                                        expanded = false
                                    }, text = { Text(item) }
                                )
                            }
                        }
                    }
                }
            )
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                WeatherScreenContent(
                    uiState = uiState,
                    modifier = modifier,
                    viewModel = viewModel,
                    selectedIndex = selectedIndex
                )
            }
        },
    )
    LaunchedEffect(selectedIndex) {
        viewModel.getWeather(Destinations.getDestination(selectedIndex))
    }

}

@Composable
fun WeatherScreenContent(
    uiState: WeatherUiState,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel?,
) {
    when {
        uiState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        uiState.errorMessage.isNotEmpty() -> {
            WeatherErrorState(uiState = uiState, viewModel = viewModel, selectedIndex = selectedIndex)
        }

        else -> {
            WeatherSuccessState(
                modifier = modifier,
                uiState = uiState,
                destinationName = Destinations.getDestinationName(selectedIndex)
            )
        }
    }
}

@Composable
private fun WeatherErrorState(
    selectedIndex: Int,
    uiState: WeatherUiState,
    viewModel: WeatherViewModel?,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = { viewModel?.getWeather(Destinations.getDestination(selectedIndex)) }) {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = "Retry",
            )
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.titleMedium,
                text = stringResource(R.string.retry),
                fontWeight = FontWeight.Bold,
            )
        }

        Text(
            modifier = Modifier
                .weight(2f)
                .alpha(0.5f)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            text = "Something went wrong: ${uiState.errorMessage}",
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun WeatherSuccessState(
    modifier: Modifier,
    uiState: WeatherUiState,
    destinationName: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val firstWeatherItem = uiState.weather?.hourlyItemMapped?.data?.first()
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = destinationName,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = firstWeatherItem?.time?.toFormattedDate().orEmpty(),
            style = MaterialTheme.typography.bodyLarge
        )

        AsyncImage(
            modifier = Modifier.size(64.dp),
            model = firstWeatherItem?.weathercode?.let { WeatherUtil.getWeatherIcon(it) },
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            error = painterResource(id = R.drawable.ic_placeholder),
            placeholder = painterResource(id = R.drawable.ic_placeholder),
        )
        Text(
            text = stringResource(
                R.string.temperature_value_in_celsius,
                uiState.weather?.hourlyItemMapped?.data?.get(0)?.temperature_2m.toString()
            ),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
        ) {
            WeatherComponent(
                modifier = Modifier.weight(1f),
                weatherLabel = stringResource(R.string.wind_speed_label),
                weatherValue = firstWeatherItem?.windspeed_10m.toString(),
                weatherUnit = stringResource(R.string.wind_speed_unit),
                iconId = R.drawable.ic_wind,
            )
            WeatherComponent(
                modifier = Modifier.weight(1f),
                weatherLabel = stringResource(R.string.elevation_label),
                weatherValue = uiState.weather?.elevation.toString(),
                weatherUnit = stringResource(R.string.elevation_unit),
                iconId = R.drawable.ic_elevation,
            )
            WeatherComponent(
                modifier = Modifier.weight(1f),
                weatherLabel = stringResource(R.string.timezone_label),
                weatherValue = uiState.weather?.timezone.toString(),
                weatherUnit = stringResource(R.string.timezone_unit),
                iconId = R.drawable.ic_timezone,
            )
        }

        Spacer(Modifier.height(16.dp))
        // Create a LazyListState to track the scroll position
        val listState = rememberLazyListState()
        // Create a state to hold the current visible date
        var currentVisibleDate by remember { mutableStateOf("") }
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(listState) {
            snapshotFlow { listState.firstVisibleItemIndex }
                .collect { index ->
                    // Get the currently visible hour based on the index
                    val visibleHour = uiState.weather?.hourlyItemMapped?.data?.getOrNull(index)
                    // Update the state with the new visible date
                    coroutineScope.launch {
                        delay(300) // Debounce delay
                        currentVisibleDate = visibleHour?.time?.toFormattedDay() ?: ""
                    }
                }
        }
        Text(
            text = if (firstWeatherItem?.time?.toFormattedDay() == currentVisibleDate) {
                stringResource(id = R.string.today)
            } else {
                currentVisibleDate
            },
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 16.dp),
        )

        // The LazyRow displaying the hourly weather data
        LazyRow(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(top = 8.dp, start = 16.dp),
        ) {
            uiState.weather?.hourlyItemMapped?.let { forecast ->
                items(forecast.data) { hour ->
                    HourlyComponent(
                        time = hour.time,
                        weatherCode = hour.weathercode,
                        temperature = stringResource(
                            R.string.temperature_value_in_celsius,
                            hour.temperature_2m,
                        )
                    )
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.forecast),
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier
                .align(Alignment.Start)
                .padding(horizontal = 16.dp),
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(top = 8.dp, start = 16.dp),
        ) {
            uiState.weather?.hourlyItemMapped?.let { hourly ->
                val groupedByDate = hourly.data.groupBy { it.time.toFormattedDay() }
                val dailyForecasts = groupedByDate.map { (date, forecasts) ->
                    val minTemp = forecasts.minOf { it.temperature_2m }
                    val maxTemp = forecasts.maxOf { it.temperature_2m }
                    val averageWeatherCode = forecasts.map { it.weathercode }.average().toInt()
                    DailyForecast(date, minTemp, maxTemp, averageWeatherCode)
                }

                items(dailyForecasts) { forecast ->
                    ForecastComponent(
                        date = forecast.date.toFormattedDay(),
                        weatherCode = forecast.weatherCode,
                        minTemp = stringResource(
                            R.string.temperature_value_in_celsius,
                            forecast.minTemp
                        ),
                        maxTemp = stringResource(
                            R.string.temperature_value_in_celsius,
                            forecast.maxTemp,
                        ),
                    )
                }
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewWeatherScreen() {
    WeatherScreen(
        viewModel = mockWeatherViewModel(),
        preferencesHelper = MockedWeatherScreen.MockPreferencesHelper()
    )
}
