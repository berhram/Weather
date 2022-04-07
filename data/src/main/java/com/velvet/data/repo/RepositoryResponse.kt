package com.velvet.data.repo

sealed class RepositoryResponse<out T> {
    data class Success<T>(val value: T) : RepositoryResponse<T>()
    object ErrorRecently : RepositoryResponse<Nothing>()
    object ErrorFailure : RepositoryResponse<Nothing>()
}