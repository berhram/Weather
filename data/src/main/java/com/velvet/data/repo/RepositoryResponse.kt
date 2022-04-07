package com.velvet.data.repo

sealed class RepositoryResponse<out T> {
    data class Success<T>(val value: T) : RepositoryResponse<T>()
    data class Recently<T>(val value: T) : RepositoryResponse<T>()
    object ErrorFailure : RepositoryResponse<Nothing>()
}