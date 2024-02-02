package com.example.buildfromgroundapp.feature.news.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildfromgroundapp.feature.news.data.model.domain.Article
import com.example.buildfromgroundapp.feature.news.domain.usecase.GetNewsFeedUsecase
import com.example.buildfromgroundapp.feature.news.domain.usecase.RefreshNewsFeedUsecase
import com.example.buildfromgroundapp.utils.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject
constructor(
    private val getNewsFeedUsecase: GetNewsFeedUsecase,
    private val refreshNewsFeedUsecase: RefreshNewsFeedUsecase
) :
    ViewModel() {

    // can be wrapped into unified view state class, but better choice is use the stateflow in compose directly
    private val _newsState = MutableStateFlow(emptyList<Article>())
    val newsState = _newsState.asStateFlow()

    private val _showProgress = MutableStateFlow(false)
    val showProgress = _showProgress.asStateFlow()

    private val _showError = MutableStateFlow(false)
    val showError = _showError.asStateFlow()

    init {
        getNewsFeed()
    }

    private fun getNewsFeed() {
        viewModelScope.launch {
            getNewsFeedUsecase().onStart {
                emit(Resource.Loading)
            }
                .catch {
                    emit(Resource.Failure(false, null, "viewModel error"))
                }
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _showProgress.value = false
                            _newsState.value = resource.value
                        }

                        is Resource.Failure -> {
                            _showProgress.value = false
                            _showError.value = true
                        }

                        is Resource.Loading -> {
                            _showProgress.value = true
                        }
                    }
                }
        }
    }

    fun refreshNewsFeed() {
        viewModelScope.launch {
            refreshNewsFeedUsecase().onStart {
                emit(Resource.Loading)
            }
                .catch {
                    emit(Resource.Failure(false, null, "viewModel error"))
                }
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _showProgress.value = false
                        }

                        is Resource.Failure -> {
                            _showProgress.value = false
                            _showError.value = true
                        }

                        is Resource.Loading -> {
                            _showProgress.value = true
                        }
                    }
                }
        }
    }

}