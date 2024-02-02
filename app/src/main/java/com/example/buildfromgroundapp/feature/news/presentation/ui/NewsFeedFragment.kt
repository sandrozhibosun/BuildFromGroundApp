package com.example.buildfromgroundapp.feature.news.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buildfromgroundapp.R
import com.example.buildfromgroundapp.databinding.FragmentNewsFeedBinding
import com.example.buildfromgroundapp.databinding.FragmentSplashScreenBinding
import com.example.buildfromgroundapp.feature.news.presentation.viewmodel.NewsFeedViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewsFeedFragment : Fragment() {

    private lateinit var binding: FragmentNewsFeedBinding
    private val viewModel: NewsFeedViewModel by viewModels()

    @Inject
    lateinit var newsFeedAdapter: NewsFeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentNewsFeedBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            newsFeedRecyclerView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = newsFeedAdapter
            }
            newsFeedAdapter.setOnItemClick { newsArticle ->
                findNavController().navigate(
                    R.id.action_newsFeedFragment_to_starWarFeedFragment,
                    Bundle().apply {
                        putParcelable("newsArticleArg", newsArticle)
                    })
            }
            swipeContainer.setOnRefreshListener {
                viewModel.refreshNewsFeed()
            }
        }
        setUpObserver()
    }

    private fun setUpObserver() {

        // only list
        viewModel.newsState.flowWithLifecycle(lifecycle).onEach { articles ->
            newsFeedAdapter.setNewsFeedList(articles)
            binding.swipeContainer.isRefreshing = false
        }.launchIn(lifecycleScope)

        viewModel.showProgress.flowWithLifecycle(lifecycle).onEach { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.swipeContainer.isRefreshing = false
        }.launchIn(lifecycleScope)

        viewModel.showError.flowWithLifecycle(lifecycle).onEach { hasError ->
            if (hasError) {
                Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT)
                    .show()
            }
            binding.swipeContainer.isRefreshing = false
        }.launchIn(lifecycleScope)
    }
}
