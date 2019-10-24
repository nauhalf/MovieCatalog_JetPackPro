package com.dicoding.naufal.moviecatalogue.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.naufal.moviecatalogue.data.source.remote.RemoteRepository
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.*
import org.junit.Rule

class MovieCatalogRepositoryTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock<RemoteRepository>{
    }
}