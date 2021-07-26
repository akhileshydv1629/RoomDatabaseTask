package com.newcompany.roomdatabasetask.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newcompany.roomdatabasetask.MainActivity
import com.newcompany.roomdatabasetask.MainActivityViewModel
import com.newcompany.roomdatabasetask.NewsViewModel
import com.newcompany.roomdatabasetask.R
import com.newcompany.roomdatabasetask.adapter.NewsAdaptor
import com.newcompany.roomdatabasetask.utils.Constants.Companion.QUERY_PAGE_SIZE
import com.newcompany.roomdatabasetask.utils.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*

class BreakingNewsFragment: Fragment(R.layout.fragment_breaking_news){

    lateinit var  viewModelNews : NewsViewModel
    lateinit var newsAdapter: NewsAdaptor
    val TAG="BreakingNewsFragment"
    override fun onViewCreated(view : View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelNews = (activity as MainActivity).viewModelNews

        setupRecyclerView()
        viewModelNews.getBreakingNews("US")

        newsAdapter.setOnItemClickListener {

            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_breakingNewsFragment_to_articleFragment4, bundle)
        }

        viewModelNews.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success ->{
                    hideProgresbar()
                    response.data?.let {
                            newsResponse -> newsAdapter.differ.submitList(newsResponse.articles.toList())
                    val totalPages = newsResponse.totalResults/ QUERY_PAGE_SIZE +2
                        isLastPage = viewModelNews.breakingNewsPage == totalPages
                        if(isLastPage)
                        {
                            rvBreakingNews.setPadding(0,0,0,0)
                        }
                    }
                }

                is Resource.Error ->{
                    hideProgresbar()
                    response.message?.let { message ->  Log.e(TAG, "An error occured") }
                }

                is Resource.Loading ->

                    showProgresbar()

            }

        })
    }

    private  fun hideProgresbar()
    {
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading =false
    }

    private  fun showProgresbar()
    {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading =true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener =object : RecyclerView.OnScrollListener()
    {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager= recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition =layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount =layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >=totalItemCount
            val isNotAtBegining = firstVisibleItemPosition >=0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE

            val shouldPaginate = isNotLoadingAndLastPage && isAtLastItem && isNotAtBegining
                    && isTotalMoreThanVisible && isScrolling

            if(shouldPaginate)
            {
                viewModelNews.getBreakingNews("US")
                isScrolling =false
            }

        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if(newState==AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
            {
                isScrolling =true
            }
        }
    }

    private  fun setupRecyclerView()
    {
        newsAdapter = NewsAdaptor()

            rvBreakingNews.apply {
                adapter = newsAdapter
                layoutManager = LinearLayoutManager(activity)

                addOnScrollListener(this@BreakingNewsFragment.scrollListener)
            }

    }

}