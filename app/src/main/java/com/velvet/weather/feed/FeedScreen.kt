package com.velvet.weather.feed

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.velvet.data.entity.City
import com.velvet.weather.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FeedScreen(viewModel: FeedViewModel, onShowCard: (cardName: String) -> Unit) {
    val state = viewModel.container.stateFlow.collectAsState()
    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collectLatest {
            when (it) {
                //TODO
            }
        }
    }
    Scaffold(topBar = {
        TopAppBar {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                IconButton(onClick = { viewModel.searchClick() }) {
                    Icon(imageVector = if (state.value.isSearchExpanded) Icons.Filled.Search else Icons.Outlined.Search, contentDescription = "Search button")
                }
            }
        }
    }) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            if (state.value.isSearchExpanded) {
                SearchBar(searchText = state.value.searchText, onChangedSearchText = { viewModel.searchCard(it) })
            }
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = state.value.isLoading),
                onRefresh = { viewModel.refresh() },
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn {
                    itemsIndexed(state.value.cities) { index: Int, item: City ->  ExpandableCard(item, viewModel = viewModel, expanded = state.value.expanded[index]) }
                }
            }
        }
    }
}

@Composable
fun ExpandableCard(
    state: CityCard,
    viewModel: FeedViewModel
) {
    Column {
        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = state.city.name)
            Text(text = state.city.temp)
            IconButton(
                onClick = { viewModel.onCityClick(state) },
                content = {
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        contentDescription = "Expandable Arrow",
                        modifier = if (state.isExpanded) Modifier.rotate(90f) else Modifier.rotate(0f)
                    )
                }
            )
        }
        ExpandableContent(visible = state.isExpanded, initialVisibility = state.isExpanded)
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
                durationMillis = 2000,
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween(2000))
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = 2000,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(2000))
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

