package com.testing.videoapp.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.testing.videoapp.data.models.Review
import com.testing.videoapp.databinding.ItemReviewBinding
import com.testing.videoapp.ui.base.BaseAdapter
import com.testing.videoapp.ui.listeners.OnLoadMoreListener
import org.koin.core.KoinComponent

class ReviewsAdapter(recyclerView: RecyclerView) : BaseAdapter<RecyclerView.ViewHolder>(),
    KoinComponent {

    var isLoading = false
    var visibleThreshold = 5
    var lastVisibleItem = 0
    var totalItemCount = 0
    var onLoadMoreListener: OnLoadMoreListener? = null

    private val listReview: MutableList<Review> = mutableListOf()

    fun setItems(results: List<Review>) {
        listReview.addAll(results)
        notifyDataSetChanged()
    }

    fun setLoadMore(onLoadMoreListener: OnLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener
    }

    fun setLoaded() {
        isLoading = false
    }

    init {
        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager!!.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener!!.onLoadMore()
                    }
                    isLoading = true
                }
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(parent.inflateBinding(ItemReviewBinding::inflate))
    }

    class ItemViewHolder(private val binding: ItemReviewBinding) : BaseViewHolder(binding) {
        fun bind(review: Review) {
            with(binding) {

                Glide.with(itemView)
                    .load(review.multimedia.imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade(500))
                    .centerCrop()
                    .into(image)

                tvTitle.text = review.title
                tvDescription.text = review.description
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bind(listReview[position])
    }

    override fun getItemCount(): Int = listReview.size

}