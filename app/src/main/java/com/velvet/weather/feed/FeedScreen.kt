package com.velvet.weather.feed

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.velvet.data.Settings.ANIMATION_DURATION
import com.velvet.weather.R

@Composable
fun FeedScreen(viewModel: FeedViewModel, onAddCity: () -> Unit) {
    val state = viewModel.container.stateFlow.collectAsState()
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
                Column() {
                    LazyColumn(Modifier.fillMaxWidth()) {
                        items(state.value.cityCards) { item: CityCard ->  ExpandableCard(item, viewModel = viewModel) }
                    }
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
            Text(text = localState.city.name + " " + localState.city.temp + "C", color = MaterialTheme.colors.onPrimary, modifier = Modifier.padding(start = 10.dp))
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
        ExpandableContent(visible = localState.isExpanded, initialVisibility = localState.isExpanded)
    }
}

@Composable
fun ExpandableContent(
    visible: Boolean = true,
    initialVisibility: Boolean = false
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
            MutableTransitionState(initialState = initialVisibility)
        }.apply { targetState = visible },
        modifier = Modifier,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut
    ) {
        Column() {
            Text(
                text = "Expandable content here",
                textAlign = TextAlign.Center
            )
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

