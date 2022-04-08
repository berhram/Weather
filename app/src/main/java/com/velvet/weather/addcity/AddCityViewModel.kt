package com.velvet.weather.addcity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velvet.data.repo.AddCityResponse
import com.velvet.data.repo.Repository
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

class AddCityViewModel(private val repository: Repository) : ContainerHost<AddCityState,AddCityEffect>,
    ViewModel()  {
    override val container: Container<AddCityState,AddCityEffect> = container(AddCityState())
    private var searchJob: Job? = null

    init {
        observeRepository()
    }

    private fun observeRepository() = intent {
        repository.getAddCityChannel().receiveAsFlow().collectLatest { error ->
            when (error) {
                AddCityResponse.FAILURE -> { postSideEffect(AddCityEffect.Error) }
                AddCityResponse.SUCCESS -> { postSideEffect(AddCityEffect.GoBack) }
            }
        }
    }

    fun findCities(keyword: String) = intent {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            reduce { state.copy(searchText = keyword) }
            delay(INPUT_DELAY)
            val response = repository.findCandidates(state.searchText)
            if (response.isSuccess) {
                val candidates = response.getOrNull()
                if (candidates != null) {
                    reduce { state.copy(candidates = candidates) }
                }
            }
        }
    }

    fun addCity(name: String, latitude: Double, longitude: Double) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCity(name = name, latitude = latitude, longitude = longitude)
        }
    }

    companion object {
        const val INPUT_DELAY = 2000L
    }
}