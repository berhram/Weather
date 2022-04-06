package com.velvet.weather.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velvet.domain.usecase.FetchCardsUseCase
import com.velvet.domain.usecase.FilterCardsUseCase
import com.velvet.domain.usecase.GetAllCardsUseCase
import com.velvet.domain.usecase.SearchCardsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class FeedViewModel(
    private val fetchWeatherUseCase: FetchCardsUseCase,
    private val searchCitiesUseCase: SearchCardsUseCase,
    private val deleteCityUseCase: GetAllCardsUseCase
    ) : ContainerHost<FeedState, FeedEffect>,
    ViewModel() {
    override val container: Container<FeedState, FeedEffect>
    private var searchJob: Job? = null

    init {
        container = fetchWeatherUseCase.invoke()
    }

    fun refresh() = intent {

    }

    fun onCityClick(city: CityCard) = intent {
        reduce {
            val cityCards = state.cityCards.toMutableList()
            cityCards[cityCards.indexOf(city)] = city.copy(isExpanded = !city.isExpanded)
            state.copy(cityCards = cityCards)
        }
    }

    fun searchCard(searchWord: String) = intent {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            reduce { state.copy(searchText = searchWord) }
            val cards = searchCardsUseCase(state.searchText)
            delay(1000)
            reduce { state.copy(cards = cards) }
        }
    }
}