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

fun <T> Flow<Resource<T>>.doOnStatusChanged(action: suspend (Status) -> Unit): Flow<Resource<T>> =
    transform { value ->
        when (value) {
            is Resource.Success -> action(Status.Content)
            is Resource.Error -> action(Status.Error(value.exception))
            Resource.Loading -> action(Status.Loading)
        }
        return@transform emit(value)
    }

fun <T> Flow<Resource<T>>.doOnLoading(action: suspend () -> Unit): Flow<Resource<T>> =
    transform { value ->
        if (value is Resource.Loading) {
            action()
        }
        return@transform emit(value)
    }

