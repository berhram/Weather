package com.velvet.data.repo

interface SafeApiCall {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ) : Result<T> {
        return try {
            Result.success(apiCall.invoke())
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}