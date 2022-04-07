package com.velvet.weather.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velvet.data.Settings
import com.velvet.data.Settings.DEFAULT_CITY_1_LATITUDE
import com.velvet.data.Settings.DEFAULT_CITY_1_LONGITUDE
import com.velvet.data.Settings.DEFAULT_CITY_1_NAME
import com.velvet.data.Settings.DEFAULT_CITY_2_LATITUDE
import com.velvet.data.Settings.DEFAULT_CITY_2_LONGITUDE
import com.velvet.data.Settings.DEFAULT_CITY_2_NAME
import com.velvet.data.repo.Repository
import com.velvet.data.repo.RepositoryResponse
import com.velvet.data.utils.isOutdated
import com.velvet.weather.R
import com.velvet.weather.ToastMaker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class FeedViewModel(
    private val repository: Repository,
    private val toastMaker: ToastMaker
    ) : ContainerHost<FeedState, FeedEffect>,
    ViewModel() {
    override val container: Container<FeedState, FeedEffect> = container(FeedState())
    private var searchJob: Job? = null

    init {
        refresh()
        checkOutdated()
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
        when (val response = repository.getWeather()) {
            is RepositoryResponse.Success -> {
                if (response.value.isNullOrEmpty()) {
                    addDefaultCities()
                }
                reduce {
                    state.copy(
                        searchText = "",
                        isSearchExpanded = false,
                        cityCards = response.value.toCityCards()
                    )
                }
            } is RepositoryResponse.Recently -> {
                viewModelScope.launch(Dispatchers.Main) { toastMaker.makeToast(R.string.already) }
                if (response.value.isNullOrEmpty()) {
                    addDefaultCities()
                }
                reduce {
                    state.copy(
                        searchText = "",
                        isSearchExpanded = false,
                        cityCards = response.value.toCityCards()
                    )
                }
            } is RepositoryResponse.ErrorFailure -> {
                viewModelScope.launch(Dispatchers.Main) { toastMaker.makeToast(R.string.error_update) }
            }
        }
    }

    fun onCityClick(id: Long) = intent {
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
            reduce { state.copy(searchText = searchWord) }
            val cities = repository.getFilteredWeather(state.searchText)
            delay(1000)
            reduce { state.copy(cityCards = cities.toCityCards()) }
        }
    }

    private fun addDefaultCities() = intent {
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