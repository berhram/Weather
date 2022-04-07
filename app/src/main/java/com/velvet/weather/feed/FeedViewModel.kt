package com.velvet.weather.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velvet.data.repo.Repository
import com.velvet.data.repo.RepositoryResponse
import com.velvet.weather.R
import com.velvet.weather.ToastMaker
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

    fun checkOutdated() {

    }

    fun searchClick() = intent {
        reduce { state.copy(isSearchExpanded = !state.isSearchExpanded) }
    }

    fun refresh() = intent {
        when (val response = repository.getWeather()) {
            is RepositoryResponse.Success -> {
                reduce {
                    state.copy(
                        searchText = "",
                        isSearchExpanded = false,
                        cityCards = response.value.toCityCards()
                    )
                }
            } is RepositoryResponse.ErrorRecently -> {
                viewModelScope.launch(Dispatchers.Main) { toastMaker.makeToast(R.string.already) }
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

    private fun addDefaultCities() = {

    }
}