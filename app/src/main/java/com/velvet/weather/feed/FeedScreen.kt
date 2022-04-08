package com.velvet.weather.feed

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.velvet.data.Settings.ANIMATION_DURATION
import com.velvet.weather.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FeedScreen(viewModel: FeedViewModel, onAddCity: () -> Unit) {
    val context = LocalContext.current
    val state = viewModel.container.stateFlow.collectAsState()
    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collectLatest {
            when (it) {
                is FeedEffect.Recently -> Toast.makeText(context, context.getString(R.string.recently_update), Toast.LENGTH_LONG).show()
                is FeedEffect.Error -> Toast.makeText(context, context.getString(R.string.error_update), Toast.LENGTH_LONG).show()
                is FeedEffect.ErrorAdd -> Toast.makeText(context, context.getString(R.string.error_add), Toast.LENGTH_LONG).show()
            }
        }
    }
    Scaffold(topBar = {
        TopAppBar(elevation = 0.dp) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = stringResource(id = R.string.app_name), style = MaterialTheme.typography.h5, color = MaterialTheme.colors.onPrimary)
                IconButton(onClick = { viewModel.searchClick() }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search button")
                }
            }
        }
    }) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            if (state.value.isSearchExpanded) {
                SearchBar(searchText = state.value.searchText, onChangedSearchText = { viewModel.searchCity(it) }, modifier = Modifier.padding(10.dp))
            }
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = state.value.isLoading),
                onRefresh = { viewModel.refresh() },
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(Modifier.fillMaxSize()) {
                    items(state.value.cityCards) { item: CityCard ->  ExpandableCard(item, viewModel = viewModel) }
                    item {
                        Button(onClick = { onAddCity() }, modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)) {
                            Text(text = stringResource(id = R.string.add_city))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ForeCastCell(date: String, min: String, max: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.padding(5.dp)) {
        Text(text = date, color = MaterialTheme.colors.onPrimary)
        Text(text = "$min°C~$max°C", color = MaterialTheme.colors.onPrimary)
    }
}

@Composable
fun ExpandableCard(
    localState: CityCard,
    viewModel: FeedViewModel
) {
    Column(
        Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary, shape = MaterialTheme.shapes.small)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Text(text = "${localState.city.name} ${localState.city.temp} °C", color = MaterialTheme.colors.onPrimary, modifier = Modifier.padding(start = 10.dp))
            IconButton(
                onClick = { viewModel.onCityClick(localState.city.id) },
                content = {
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        contentDescription = "Expandable Arrow",
                        modifier = if (localState.isExpanded) Modifier.rotate(180f) else Modifier.rotate(0f),
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            )
        }
        ExpandableContent(localState, onDelete = { viewModel.deleteCity(localState.city.id) })
    }
}

@Composable
fun ExpandableContent(
    localState : CityCard, onDelete: () -> Unit
) {
    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = ANIMATION_DURATION,
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween(ANIMATION_DURATION))
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = ANIMATION_DURATION,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(ANIMATION_DURATION))
    }
    AnimatedVisibility(
        visibleState = remember {
            MutableTransitionState(initialState = localState.isExpanded)
        }.apply { targetState = localState.isExpanded },
        modifier = Modifier,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut
    ) {
        Column {
            Text(text = stringResource(R.string.update_time) + " ${localState.city.humanTime}", color = MaterialTheme.colors.onPrimary, modifier = Modifier.padding(10.dp))
            Text(text = stringResource(R.string.feels_like) + " ${localState.city.feelsLike}", color = MaterialTheme.colors.onPrimary, modifier = Modifier.padding(10.dp))
            Text(text = stringResource(R.string.humidity) + " ${localState.city.humidity}", color = MaterialTheme.colors.onPrimary, modifier = Modifier.padding(10.dp))
            Text(text = stringResource(R.string.clouds) + " ${localState.city.clouds}", color = MaterialTheme.colors.onPrimary, modifier = Modifier.padding(10.dp))
            Text(text = stringResource(R.string.visibility) + " ${localState.city.visibility}", color = MaterialTheme.colors.onPrimary, modifier = Modifier.padding(10.dp))
            Text(text = stringResource(R.string.wind_speed) + " ${localState.city.windSpeed}", color = MaterialTheme.colors.onPrimary, modifier = Modifier.padding(10.dp))
            Text(text = stringResource(R.string.wind_deg) + " ${localState.city.windDeg}", color = MaterialTheme.colors.onPrimary, modifier = Modifier.padding(10.dp))
            Text(text = stringResource(R.string.pressure) + " ${localState.city.pressure}", color = MaterialTheme.colors.onPrimary, modifier = Modifier.padding(10.dp))
            LazyRow(
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth()) {
                items(localState.city.dailyWeather) {
                    item -> ForeCastCell(date = item.date, min = item.tempMin, max = item.tempMax)
                }
            }
            Button(onClick = { onDelete() }, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary, contentColor = MaterialTheme.colors.onSecondary), modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
                Text(text = stringResource(R.string.delete), color = MaterialTheme.colors.onSecondary)
            }
        }
    }
}


@Composable
fun SearchBar(
    searchText: String,
    onChangedSearchText: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        OutlinedTextField(
            value = searchText,
            onValueChange = onChangedSearchText,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text =  stringResource(R.string.search)) }
        )
    }
}

