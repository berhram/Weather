package com.velvet.weather.feed

import com.velvet.data.entity.City

data class FeedState
    (val isLoading: Boolean,
     val cityCards: List<CityCard>,
     val searchText: String,
     val isSearchExpanded: Boolean
) {
    companion object {
        fun Initial(cities: List<City>) : FeedState {
            val cards = ArrayList<CityCard>()
            for (city in cities) {
                cards.add(CityCard(city, false))
            }
            return FeedState(
                isLoading = false,
                cityCards = cards,
                searchText = "",
                isSearchExpanded = false
            )
        }

    }
}