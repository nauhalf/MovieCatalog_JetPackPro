package com.dicoding.naufal.moviecatalogue.ui.main.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dicoding.naufal.moviecatalogue.data.MovieCatalogRepository
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.FilmEntity
import com.dicoding.naufal.moviecatalogue.data.source.remote.response.FilmsResponse
import com.dicoding.naufal.moviecatalogue.parseJsonFromPath
import com.dicoding.naufal.moviecatalogue.ui.main.discovery.movie.MovieViewModel
import com.dicoding.naufal.moviecatalogue.utils.CoroutinesTestRule
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel
    private val repo = mock(MovieCatalogRepository::class.java)
    private var dummmyMovie: List<FilmEntity>? = null


    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = MovieViewModel(repo)
        val film = parseJsonFromPath<FilmsResponse>("discover_movie.json").filmResponse

        dummmyMovie = film?.map {
            FilmEntity.toFilmEntity(it)
        }
    }

    @Test
    fun getFilms() = runBlocking {

        `when`(repo.getDiscoveryMovie(1)).thenReturn(dummmyMovie)

        viewModel.getMovie(1)

        val observer = mock(Observer::class.java) as Observer<List<FilmEntity>>

        viewModel.getMovieLiveData().observeForever(observer)
        Mockito.verify(observer).onChanged(dummmyMovie)
    }
}