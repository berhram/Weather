package com.velvet.weather.feed

import com.velvet.data.entity.City

data class FeedState(
    val isLoading: Boolean = false,
    val cityCards: List<CityCard> = emptyList(),
    val searchText: String = "",
    val isSearchExpanded: Boolean = false
)

fun FeedState.setCities(cities: List<City>) : FeedState {
    val cards = ArrayList<CityCard>()
    for (city in cities) {
        cards.add(CityCard(city, false))
    }
    return this.copy(
        cityCards = cards
    )
}

fun List<City>.toCityCards() : List<CityCard> {
    val cards = ArrayList<CityCard>()
    for (city in this) {
        cards.add(CityCard(city, false))
    }
    return cards
}