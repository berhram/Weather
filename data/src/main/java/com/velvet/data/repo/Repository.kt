package com.velvet.data.repo

interface Repository {
    suspend fun getCards() : List<Card>
    suspend fun getCard(cardName: String) : Card
    suspend fun fetch()
}