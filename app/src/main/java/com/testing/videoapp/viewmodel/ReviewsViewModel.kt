package com.testing.videoapp.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testing.videoapp.data.Resource
import com.testing.videoapp.data.models.ReviewModel
import com.testing.videoapp.data.repository.ReviewsRepository
import com.testing.videoapp.ui.base.TAG
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.dsl.koinApplication
import kotlin.coroutines.CoroutineContext

class ReviewsViewModel(private val reviewsRepository: ReviewsRepository) : ViewModel() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(viewModelScope.coroutineContext + job + coroutineHandler())
    private val mutableFlowReviews = MutableStateFlow<Resource<ReviewModel>>(Resource.loading(null))
    private var offset = 0

    init {
        fetchData()
    }

    fun getFlowReviews(): StateFlow<Resource<ReviewModel>> {
        return mutableFlowReviews.asStateFlow()
    }

    fun fetchData() {
        scope.launch {
            mutableFlowReviews.value = Resource.loading(null)
            val data = reviewsRepository.getReviews(offset)
            mutableFlowReviews.value = Resource.success(data)
        }
    }

    fun increaseOffset(value: Int) {
        offset += value
        Log.i(TAG(), "increase offset is $offset")
    }

    fun reduceOffset(value: Int) {
        offset -= value
        Log.e(TAG(), "reduce offset is $offset")
    }

    private fun coroutineHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _: CoroutineContext, exception: Throwable ->
            run {
                Log.e(TAG(), exception.message.toString(), exception)
                mutableFlowReviews.value = Resource.error(exception.message.toString(), null)
            }
        }
    }
}