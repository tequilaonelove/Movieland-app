package com.testing.videoapp.ui.base

import android.app.Activity
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.transform
import kotlin.coroutines.CoroutineContext

fun ViewModel.TAG(): String = this::class.java.simpleName

private fun handlerError(TAG: String, throwable: Throwable) =
    Log.e(TAG, throwable.message.toString(), throwable)

fun <T> Flow<T>.onEachAndCatch(tag: String, action: suspend (T) -> Unit): Flow<T> =
    transform { value ->
        action(value)
        emit(value)
    }.catch { handlerError(tag, it) }

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}