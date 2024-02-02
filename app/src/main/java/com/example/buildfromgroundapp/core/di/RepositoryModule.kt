package com.example.buildfromgroundapp.core.di

import com.example.buildfromgroundapp.feature.news.data.repository.NewsFeedRepository
import com.example.buildfromgroundapp.feature.news.data.repository.NewsFeedRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindNewsApiRepository(
        impl: NewsFeedRepositoryImpl
    ): NewsFeedRepository
}

/**
 *  instructing Dagger to provide instances of NewsFeedRepository by creating instances of NewsFeedRepositoryImpl. Here's why @Binds is used for this purpose
 *
 *  Efficiency: @Binds generates less code than @Provides, @Binds annotation abstracts away the need for a method body(instance), making the module more concise.
 *  Dagger internally figures out how to provide an instance of NewsFeedRepositoryImpl whenever NewsFeedRepository is required.
 *
 *  Declarative Approach: provide impl when there's a needs for interface, it's the advantage of abstractions cuz might have different implementation of same baseclass in different module.
 *
 *  Polymorphism:  can easily swap out NewsFeedRepositoryImpl with another implementation of NewsFeedRepository without changing the code that consumes this dependency.
 *  This is especially useful for testing, where you might want to provide a mock or fake implementation of NewsFeedRepository.
 */

