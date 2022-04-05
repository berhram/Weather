package com.velvet.data.network

interface Network {
    suspend fun getCards() : Result<List<CardScheme>>
}