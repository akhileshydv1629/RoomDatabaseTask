package com.newcompany.roomdatabasetask.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.newcompany.roomdatabasetask.MainActivity
import com.newcompany.roomdatabasetask.MainActivityViewModel
import com.newcompany.roomdatabasetask.R
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment: Fragment(R.layout.fragment_article){
    lateinit var viewModel : MainActivityViewModel
    val args: ArticleFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel


        val article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url!!)
        }

    }

}