package com.velvet.weather.addcity

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.velvet.data.schemas.geo.CitySchema
import com.velvet.weather.R
import com.velvet.weather.feed.SearchBar
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddCityScreen(viewModel: AddCityViewModel, goBack: () -> Unit) {
    val state = viewModel.container.stateFlow.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collectLatest {
            when (it) {
                is AddCityEffect.GoBack -> goBack()
                is AddCityEffect.Error -> Toast.makeText(context, context.getText(R.string.error), Toast.LENGTH_LONG).show()
            }
        }
    }
    Scaffold(topBar = {
        TopAppBar {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { goBack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Search button")
                }
                Text(text = stringResource(id = R.string.find_city), style = MaterialTheme.typography.h5, color = MaterialTheme.colors.onPrimary)
            }
        }
    }) {
        Column(Modifier.fillMaxSize()) {
            SearchBar(searchText = state.value.searchText, onChangedSearchText = { viewModel.findCities(it) }, modifier = Modifier.padding(10.dp))
            LazyColumn() {
                items(state.value.candidates) {
                    item ->  CandidateCard(viewModel = viewModel, candidate = item)
                }
            }
        }
    }
}

@Composable
fun CandidateCard(viewModel: AddCityViewModel, candidate: CitySchema) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            viewModel.addCity(
                name = candidate.name,
                latitude = candidate.latitude,
                longitude = candidate.longitude
            )
        }.padding(10.dp).fillMaxWidth()
    ) {
        Text(text = candidate.name + ", " + candidate.country)
    }
}