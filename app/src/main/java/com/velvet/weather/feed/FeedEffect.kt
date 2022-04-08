package com.velvet.weather.feed

sealed class FeedEffect {
    object Recently : FeedEffect()
    object Error : FeedEffect()
    object ErrorAdd : FeedEffect()
}