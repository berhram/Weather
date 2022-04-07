package com.velvet.weather.feed

sealed class FeedEffect {
    object GoAddCity : FeedEffect()
}