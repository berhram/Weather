package com.velvet.weather.feed

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.velvet.data.CityCard
import com.velvet.weather.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FeedScreen(viewModel: FeedViewModel, onShowCard: (cardName: String) -> Unit) {
    val state = viewModel.container.stateFlow.collectAsState()
    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collectLatest {
            when (it) {
                is FeedEffect.ShowCard -> onShowCard(it.cardName)
            }
        }
    }
    Scaffold(topBar = {
        TopAppBar(elevation = 0.dp, modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = stringResource(id = R.string.tarot_dir),
                    textAlign = TextAlign.Start)
                IconButton(onClick = { viewModel.filterClick() }) {

                }
            }
        }
    }) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            SearchBar(searchText = state.value.searchText, onChangedSearchText = { viewModel.searchCard(it) })
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = state.value.isLoading),
                onRefresh = { viewModel.refresh() },
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn {
                    if (state.value.cards.isNullOrEmpty()) {
                        items(items = listOf(System.currentTimeMillis()), key = { it }) {
                            Column(
                                modifier = Modifier
                                    .fillParentMaxHeight()
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    textAlign = TextAlign.Center,
                                    text = stringResource(id = R.string.no_cards),
                                )
                            }
                        }
                    } else {
                        items(
                            items = state.value.cards,
                            key = { it.name }
                        ) { CardItem(it, viewModel = viewModel) }
                    }
                }
            }
        }
    }
}

@Composable
fun ExpandableCard(
    data: CityCard,
    onCardArrowClick: () -> Unit,
    expanded: Boolean,
) {
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(transitionState)

    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = 2000)
    }) {
        if (expanded) 0f else 180f
    }

    Column {
        Row {
            Text(text = )
            IconButton(
                onClick = onClick,
                content = {
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        contentDescription = "Expandable Arrow",
                        modifier = Modifier.rotate(arrowRotationDegree),
                    )
                }
            )
        }
        ExpandableContent(visible = expanded, initialVisibility = expanded)
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
        Column(modifier = Modifier.padding(8.dp)) {
            Spacer(modifier = Modifier.heightIn(100.dp))
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

