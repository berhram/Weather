package com.velvet.weather.feed

data class FeedState
    (val isLoading: Boolean = false,
     val cities: List<CityCard> = emptyList(),
     val searchText: String = "",
     val isSearchExpanded: Boolean = false)