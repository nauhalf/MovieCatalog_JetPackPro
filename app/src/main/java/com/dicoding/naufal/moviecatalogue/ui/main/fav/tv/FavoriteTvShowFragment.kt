package com.dicoding.naufal.moviecatalogue.ui.main.fav.tv


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.jetpack.nauhalf.academy.utils.vo.Status
import com.dicoding.naufal.moviecatalogue.BR
import com.dicoding.naufal.moviecatalogue.R
import com.dicoding.naufal.moviecatalogue.base.BaseFragment
import com.dicoding.naufal.moviecatalogue.databinding.FragmentFavoriteTvShowBinding
import com.dicoding.naufal.moviecatalogue.ui.detail.movie.DetailMovieActivity
import com.dicoding.naufal.moviecatalogue.ui.main.FilmItemDecoration
import com.dicoding.naufal.moviecatalogue.ui.main.fav.FavoriteAdapter
import com.dicoding.naufal.moviecatalogue.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_favorite_tv_show.*


/**
 * A simple [Fragment] subclass.
 */
class FavoriteTvShowFragment :
    BaseFragment<FragmentFavoriteTvShowBinding, FavoriteTvShowViewModel>() {

    private lateinit var mFavoriteTvShowViewModel: FavoriteTvShowViewModel
    private lateinit var mFavoriteTvShowBinding: FragmentFavoriteTvShowBinding
    private lateinit var adapter: FavoriteAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_favorite_tv_show
    }

    override fun getViewModel(): FavoriteTvShowViewModel {
        mFavoriteTvShowViewModel =
            ViewModelProviders.of(this, ViewModelFactory.getInstance(requireActivity().application))
                .get(FavoriteTvShowViewModel::class.java)
        return mFavoriteTvShowViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.favoriteTvShowViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFavoriteTvShowBinding = getViewDataBinding()
        setUp()
    }

    private fun setUp() {
        adapter = FavoriteAdapter(onClickListener = {
            startActivity(DetailMovieActivity.newIntent(requireContext(), it.filmId))
        })

        recycler_fav_tv.apply {
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
        mFavoriteTvShowViewModel.favTvLiveData.observe(viewLifecycleOwner, Observer { resource ->
            if (resource != null) {
                when (resource.status) {
                    Status.LOADING -> {
                        mFavoriteTvShowViewModel.setLoading(true)
                    }

                    Status.SUCCESS -> {
                        adapter.submitList(resource.data)
                        mFavoriteTvShowViewModel.setLoading(false)
                    }

                    Status.ERROR -> {
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT)
                            .show()
                        mFavoriteTvShowViewModel.setLoading(false)
                    }
                }
            }
        })
    }
}
