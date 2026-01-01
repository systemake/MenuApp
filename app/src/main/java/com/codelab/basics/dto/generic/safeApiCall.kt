package com.codelab.basics.dto.generic


suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): ApiResult<T> {
    return try {
        ApiResult.Success(apiCall())
    } catch (e: Exception) {
        ApiResult.Error(
            message = e.localizedMessage ?: "Unknown error",
            throwable = e
        )
    }
}