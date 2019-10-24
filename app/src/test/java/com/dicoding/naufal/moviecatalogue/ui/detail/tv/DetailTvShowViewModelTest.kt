package com.dicoding.naufal.moviecatalogue.ui.detail.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dicoding.naufal.moviecatalogue.data.MovieCatalogRepository
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.TvEntity
import com.dicoding.naufal.moviecatalogue.data.source.remote.response.TvsResponse
import com.dicoding.naufal.moviecatalogue.parseJsonFromPath
import com.dicoding.naufal.moviecatalogue.utils.CoroutinesTestRule
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class DetailTvShowViewModelTest {
    private lateinit var viewModel: DetailTvShowViewModel
    private val repo = mock(MovieCatalogRepository::class.java)
    private var dummyTv: TvEntity? = null

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        viewModel = DetailTvShowViewModel(repo)
        val tvs = parseJsonFromPath<TvsResponse>("detail_tv.json")
        val tvResponse = tvs.tvResponses?.find { it.id == 25917 }
        dummyTv = TvEntity.toTv(tvResponse)
    }

    @Test
    fun getSelectedTv() = runBlocking {
        Mockito.`when`(repo.getTv(25917)).thenReturn(dummyTv)

        viewModel.setTvLiveData(25917)

        val observer = mock(Observer::class.java) as Observer<TvEntity>

        viewModel.getTvLiveData().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyTv)
    }

}