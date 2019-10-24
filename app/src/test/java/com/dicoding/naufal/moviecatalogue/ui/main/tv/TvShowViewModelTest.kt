package com.dicoding.naufal.moviecatalogue.ui.main.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dicoding.naufal.moviecatalogue.data.MovieCatalogRepository
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.FilmEntity
import com.dicoding.naufal.moviecatalogue.data.source.remote.response.FilmsResponse
import com.dicoding.naufal.moviecatalogue.parseJsonFromPath
import com.dicoding.naufal.moviecatalogue.utils.CoroutinesTestRule
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class TvShowViewModelTest {

    private lateinit var viewModel: TvShowViewModel
    private val repo = Mockito.mock(MovieCatalogRepository::class.java)
    private var dummyTv: List<FilmEntity>? = null

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(repo)
        val films = parseJsonFromPath<FilmsResponse>("discover_tv.json").filmResponse
        dummyTv = films?.map {
            FilmEntity.toFilmEntity(it)
        }
    }

    @Test
    fun getFilms() = runBlocking {
        Mockito.`when`(repo.getDiscoveryTv(1)).thenReturn(dummyTv)

        viewModel.getTv(1)

        val observer = Mockito.mock(Observer::class.java) as Observer<List<FilmEntity>>

        viewModel.getTvLiveData().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyTv)
    }

}