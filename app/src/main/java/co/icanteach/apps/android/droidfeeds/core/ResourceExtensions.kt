package co.icanteach.apps.android.droidfeeds.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

fun <T> Flow<Resource<T>>.doOnSuccess(action: suspend (T) -> Unit): Flow<Resource<T>> =
    transform { value ->
        if (value is Resource.Success) {
            action(value.data)
        }
        return@transform emit(value)
    }

fun <T> Flow<Resource<T>>.doOnError(action: suspend (Throwable) -> Unit): Flow<Resource<T>> =
    transform { value ->
        if (value is Resource.Error) {
            action(value.exception)
        }
        return@transform emit(value)
    }
