package com.codelab.basics.dto.generic

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(
        val message: String,
        val code: Int? = null,
        val throwable: Throwable? = null
    ) : ApiResult<Nothing>()
}

inline fun <T> ApiResult<T>.onSuccess(action: (T) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) action(data)
    return this
}

inline fun ApiResult<*>.onError(action: (ApiResult.Error) -> Unit) {
    if (this is ApiResult.Error) action(this)
}

fun <T> ApiResult<T>.getOrNull(): T? =
    if (this is ApiResult.Success) data else null
