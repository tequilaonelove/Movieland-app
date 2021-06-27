package com.testing.videoapp.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.testing.videoapp.R
import com.testing.videoapp.databinding.ActivityMainBinding
import com.testing.videoapp.ui.adapters.ReviewsAdapter
import com.testing.videoapp.ui.base.invisible
import com.testing.videoapp.ui.base.onEachAndCatch
import com.testing.videoapp.ui.base.show
import com.testing.videoapp.ui.listeners.OnLoadMoreListener
import com.testing.videoapp.viewmodel.ReviewsViewModel
import com.testing.videoapp.viewmodel.states.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName
    private val binding by viewBinding(ActivityMainBinding::bind)
    private val reviewsViewModel: ReviewsViewModel by viewModel()
    private lateinit var reviewsAdapter: ReviewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(binding) {

            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            reviewsAdapter = ReviewsAdapter(recyclerView)
            recyclerView.adapter = reviewsAdapter

            lifecycleScope.launch {
                reviewsViewModel.getFlowReviews().onEachAndCatch(TAG) {
                    when (it.status) {
                        Status.LOADING -> {
                            progressBar.show()
                        }
                        Status.SUCCESS -> {
                            progressBar.invisible()
                            reviewsAdapter.setLoaded()
                            it.data?.let { reviewModel ->
                                reviewsAdapter.setItems(reviewModel.results)
                            }
                        }
                        Status.ERROR -> {
                            progressBar.invisible()
                            reviewsAdapter.setLoaded()
                            reviewsViewModel.reduceOffset(20)
                            Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }.collect()
            }

        }

        reviewsAdapter.setLoadMore(object : OnLoadMoreListener {
            override fun onLoadMore() {
                reviewsViewModel.increaseOffset(20)
                reviewsViewModel.fetchData()
            }
        })

    }

}