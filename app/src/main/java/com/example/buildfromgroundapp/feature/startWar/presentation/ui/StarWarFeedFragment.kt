package com.example.buildfromgroundapp.feature.startWar.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buildfromgroundapp.databinding.FragmentNewsFeedBinding
import com.example.buildfromgroundapp.databinding.FragmentStarwarFeedBinding
import com.example.buildfromgroundapp.feature.news.presentation.ui.NewsFeedAdapter
import com.example.buildfromgroundapp.feature.news.presentation.viewmodel.NewsFeedViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StarWarFeedFragment : Fragment() {

    private lateinit var binding: FragmentStarwarFeedBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentStarwarFeedBinding.inflate(inflater, container, false)
        val args: StarWarFeedFragmentArgs by navArgs()
        binding.starwarFeedTitle.text = args.newsArticleArg.title

        return binding.root
    }

    private fun setUpObserver() {


    }
}