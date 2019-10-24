package com.dicoding.naufal.moviecatalogue.ui.main.movie


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.jetpack.nauhalf.academy.utils.vo.Status
import com.dicoding.naufal.moviecatalogue.BR
import com.dicoding.naufal.moviecatalogue.R
import com.dicoding.naufal.moviecatalogue.base.BaseFragment
import com.dicoding.naufal.moviecatalogue.databinding.FragmentMovieBinding
import com.dicoding.naufal.moviecatalogue.ui.detail.movie.DetailMovieActivity
import com.dicoding.naufal.moviecatalogue.ui.main.FilmAdapter
import com.dicoding.naufal.moviecatalogue.ui.main.FilmItemDecoration
import com.dicoding.naufal.moviecatalogue.ui.main.discovery.movie.MovieViewModel
import com.dicoding.naufal.moviecatalogue.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_movie.*


class MovieFragment : BaseFragment<FragmentMovieBinding, MovieViewModel>() {

    private lateinit var mFilmAdapter: FilmAdapter
    private lateinit var mMovieViewModel: MovieViewModel
    private lateinit var mFragmentMovieBinding: FragmentMovieBinding

    override fun getLayoutId(): Int = R.layout.fragment_movie

    override fun getViewModel(): MovieViewModel {


        mMovieViewModel = ViewModelProviders.of(
            this, ViewModelFactory.getInstance(requireActivity().application)
        ).get(MovieViewModel::class.java)
        return mMovieViewModel
    }

    override fun getBindingVariable(): Int = BR.movieViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFragmentMovieBinding = getViewDataBinding()
        setUp()
    }

    private fun setUp() {

        mFilmAdapter = FilmAdapter(mutableListOf()) {
            startActivity(DetailMovieActivity.newIntent(requireContext(), it.id))
        }

        recycler_movie.apply {
            adapter = mFilmAdapter
            layoutManager = GridLayoutManager(
                requireContext(),
                resources.getInteger(R.integer.discovery_columns)
            )
            addItemDecoration(
                FilmItemDecoration(resources.configuration.orientation, 16)
            )
        }

        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        mMovieViewModel.getMovieLiveData().observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    mMovieViewModel.setLoading(true)
                }

                Status.SUCCESS -> {
                    mFilmAdapter.addFilms(resource.data)
                    mMovieViewModel.setLoading(false)
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                    mMovieViewModel.setLoading(false)
                }
            }
        })
    }

}
