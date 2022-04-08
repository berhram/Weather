package com.velvet.weather.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velvet.data.Settings.DEFAULT_CITY_1_LATITUDE
import com.velvet.data.Settings.DEFAULT_CITY_1_LONGITUDE
import com.velvet.data.Settings.DEFAULT_CITY_1_NAME
import com.velvet.data.Settings.DEFAULT_CITY_2_LATITUDE
import com.velvet.data.Settings.DEFAULT_CITY_2_LONGITUDE
import com.velvet.data.Settings.DEFAULT_CITY_2_NAME
import com.velvet.data.repo.Repository
import com.velvet.data.repo.FeedResponse
import com.velvet.data.utils.isOutdated
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class FeedViewModel(
    private val repository: Repository
    ) : ContainerHost<FeedState, FeedEffect>,
    ViewModel() {
    override val container: Container<FeedState, FeedEffect> = container(FeedState())
    private var searchJob: Job? = null

    init {
        observeData()
        observeResponses()
        refresh()
        checkOutdated()
    }

    private fun observeData() = intent(registerIdling = false) {
        repository.getData().collect { cities ->
            if (cities.isEmpty()) {
                addDefaultCities()
            }
            reduce {
                state.copy(
                    cityCards = cities.filter {
                        city -> city.name.contains(
                        other = state.searchText,
                        ignoreCase = true
                        )
                    }.toCityCards()
                )
            }
        }
    }

    private fun observeResponses() = intent(registerIdling = false) {
        repository.getFeedChannel().receiveAsFlow().collectLatest { error ->
            when (error) {
                FeedResponse.FAILURE -> { postSideEffect(FeedEffect.Error) }
                FeedResponse.RECENTLY -> { postSideEffect(FeedEffect.Recently) }
                FeedResponse.FAILURE_ADD -> { postSideEffect(FeedEffect.ErrorAdd) }
            }
        }
    }

    private fun checkOutdated() = intent {
        for (city in state.cityCards) {
            if (city.city.time.isOutdated()) {
                reduce {
                    state.copy(isOutdated = true)
                }
            }
        }
    }

    fun searchClick() = intent {
        reduce { state.copy(isSearchExpanded = !state.isSearchExpanded) }
    }

    fun refresh() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchWeather()
        }
    }

    fun onCityClick(id: String) = intent {
        val cityCards = state.cityCards.toMutableList()
        for (card in cityCards) {
            if (card.city.id == id) {
                cityCards[cityCards.indexOf(card)] = card.copy(isExpanded = !card.isExpanded)
                reduce { state.copy(cityCards = cityCards) }
            }
        }
    }

    fun searchCity(searchWord: String) = intent {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            reduce { state.copy(searchText = searchWord) }
        }
    }

    fun deleteCity(id: String) = intent {
        repository.delete(id)
    }

    private fun addDefaultCities() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCity(
                name =  DEFAULT_CITY_1_NAME,
                longitude = DEFAULT_CITY_1_LONGITUDE,
                latitude = DEFAULT_CITY_1_LATITUDE
            )
            repository.addCity(
                name =  DEFAULT_CITY_2_NAME,
                longitude = DEFAULT_CITY_2_LONGITUDE,
                latitude = DEFAULT_CITY_2_LATITUDE
            )
        }
    }
}