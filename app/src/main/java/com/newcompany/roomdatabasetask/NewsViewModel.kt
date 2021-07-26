package com.newcompany.roomdatabasetask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newcompany.roomdatabasetask.api.UserApi
import com.newcompany.roomdatabasetask.model.Article
import com.newcompany.roomdatabasetask.model.NewsResponse
import com.newcompany.roomdatabasetask.repository.UserRepository
import com.newcompany.roomdatabasetask.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class NewsViewModel (
    application: Application
): AndroidViewModel(application) {

     val breakingNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null

    val searchNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsResponse: NewsResponse? = null


    var searchNewsPage = 1
    @Inject
    lateinit var apiService: UserApi
    @Inject
    lateinit var userRepository: UserRepository

    init {
        (application as MyApp).getAppComponent().inject(this)
    }

    fun getBreakingNews(countrycode : String) = viewModelScope.launch {

        breakingNews.postValue(Resource.Loading())
        val response = userRepository.getBreakingNews(countrycode, breakingNewsPage, apiService)

        breakingNews.postValue(handleBreakingNewsResponse(response))
    }
    fun searchNews(searchQuery : String) = viewModelScope.launch {

        searchNews.postValue(Resource.Loading())
        val response = userRepository.searchNews(searchQuery, searchNewsPage, apiService)

        searchNews.postValue(handleSearchNewsResponse(response))
    }


    private fun handleBreakingNewsResponse(response : Response<NewsResponse>) : Resource<NewsResponse>
    {
        if(response.isSuccessful)
        {
            response.body()?.let { resultRespnse ->

                breakingNewsPage++
                if(breakingNewsResponse== null)
                {
                    breakingNewsResponse= resultRespnse
                }
                else
                {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles= resultRespnse?.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResponse?:resultRespnse)
            }
        }

        return Resource.Error(response.message())
    }
    private fun handleSearchNewsResponse(response : Response<NewsResponse>) : Resource<NewsResponse>
    {
        if(response.isSuccessful)
        {
            response.body()?.let { resultRespnse ->

                searchNewsPage++
                if(searchNewsResponse== null)
                {
                    searchNewsResponse= resultRespnse
                }
                else
                {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles= resultRespnse?.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse?:resultRespnse)
            }
        }

        return Resource.Error(response.message())
    }

}