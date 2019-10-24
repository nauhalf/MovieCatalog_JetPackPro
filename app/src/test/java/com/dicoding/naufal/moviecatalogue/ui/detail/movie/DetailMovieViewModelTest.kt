package com.dicoding.naufal.moviecatalogue.ui.detail.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dicoding.naufal.moviecatalogue.data.MovieCatalogRepository
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.MovieEntity
import com.dicoding.naufal.moviecatalogue.data.source.remote.response.MoviesResponse
import com.dicoding.naufal.moviecatalogue.parseJsonFromPath
import com.dicoding.naufal.moviecatalogue.utils.CoroutinesTestRule
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class DetailMovieViewModelTest {
    private lateinit var viewModel: DetailMovieViewModel
    private var dummyMovie: MovieEntity? = null
    private val repo = mock(MovieCatalogRepository::class.java)

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel(repo)
        val movies = parseJsonFromPath<MoviesResponse>("detail_movie.json")
        val movieResponse = movies.movieResponses?.find { it.id == 381344 }
        dummyMovie = MovieEntity.toMovie(movieResponse)
    }

    @Test
    fun getSelectedTv() = runBlocking {
        Mockito.`when`(repo.getMovie(381344)).thenReturn(dummyMovie)

        viewModel.setMovieLiveData(381344)

        val observer = mock(Observer::class.java) as Observer<MovieEntity>

        viewModel.getMovieLiveData().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovie)
    }
}