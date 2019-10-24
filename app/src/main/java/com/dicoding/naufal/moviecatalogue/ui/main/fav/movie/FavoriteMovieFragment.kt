package com.dicoding.naufal.moviecatalogue.ui.main.fav.movie


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.jetpack.nauhalf.academy.utils.vo.Status
import com.dicoding.naufal.moviecatalogue.BR
import com.dicoding.naufal.moviecatalogue.R
import com.dicoding.naufal.moviecatalogue.base.BaseFragment
import com.dicoding.naufal.moviecatalogue.databinding.FragmentFavoriteMovieBinding
import com.dicoding.naufal.moviecatalogue.ui.detail.movie.DetailMovieActivity
import com.dicoding.naufal.moviecatalogue.ui.main.FilmItemDecoration
import com.dicoding.naufal.moviecatalogue.ui.main.fav.FavoriteAdapter
import com.dicoding.naufal.moviecatalogue.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_favorite_movie.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMovieFragment : BaseFragment<FragmentFavoriteMovieBinding, FavoriteMovieViewModel>() {

    private lateinit var mFavoriteMovieBinding: FragmentFavoriteMovieBinding
    private lateinit var mFavoriteMovieViewModel: FavoriteMovieViewModel
    private lateinit var adapter: FavoriteAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_favorite_movie
    }

    override fun getViewModel(): FavoriteMovieViewModel {
        mFavoriteMovieViewModel = ViewModelProviders.of(
            this, ViewModelFactory.getInstance(requireActivity().application)
        ).get(FavoriteMovieViewModel::class.java)
        return mFavoriteMovieViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.favoriteMovieViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFavoriteMovieBinding = getViewDataBinding()
        setUp()
    }

    private fun setUp() {
        adapter = FavoriteAdapter(onClickListener = {
            startActivity(DetailMovieActivity.newIntent(requireContext(), it.filmId))
        })

        recycler_fav_movie.apply {
            adapter = this.adapter
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
        mFavoriteMovieViewModel.favMovieLiveData.observe(viewLifecycleOwner, Observer { resource ->
            if (resource != null) {
                when (resource.status) {
                    Status.LOADING -> {
                        mFavoriteMovieViewModel.setLoading(true)
                    }

                    Status.SUCCESS -> {
                        adapter.submitList(resource.data)
                        mFavoriteMovieViewModel.setLoading(false)
                    }

                    Status.ERROR -> {
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT)
                            .show()
                        mFavoriteMovieViewModel.setLoading(false)
                    }
                }
            }
        })
    }
}
