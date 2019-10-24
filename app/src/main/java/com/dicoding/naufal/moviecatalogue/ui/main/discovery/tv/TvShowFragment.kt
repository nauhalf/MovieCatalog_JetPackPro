package com.dicoding.naufal.moviecatalogue.ui.main.tv

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
import com.dicoding.naufal.moviecatalogue.databinding.FragmentTvShowBinding
import com.dicoding.naufal.moviecatalogue.ui.detail.tv.DetailTvShowActivity
import com.dicoding.naufal.moviecatalogue.ui.main.FilmAdapter
import com.dicoding.naufal.moviecatalogue.ui.main.FilmItemDecoration
import com.dicoding.naufal.moviecatalogue.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_tv_show.*

class TvShowFragment : BaseFragment<FragmentTvShowBinding, TvShowViewModel>() {

    private lateinit var mFilmAdapter: FilmAdapter
    private lateinit var mTvShowViewModel: TvShowViewModel
    private lateinit var mTvShowBinding: FragmentTvShowBinding

    override fun getLayoutId(): Int = R.layout.fragment_tv_show

    override fun getViewModel(): TvShowViewModel {
        mTvShowViewModel = ViewModelProviders.of(
            this, ViewModelFactory.getInstance(requireActivity().application)
        ).get(TvShowViewModel::class.java)
        return mTvShowViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.tvShowViewModel
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTvShowBinding = getViewDataBinding()
        setUp()
    }

    private fun setUp() {
        mFilmAdapter = FilmAdapter(mutableListOf()) {
            startActivity(DetailTvShowActivity.newIntent(requireContext(), it.id))
        }

        recycler_tv.apply {
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
        mTvShowViewModel.getTvLiveData().observe(viewLifecycleOwner, Observer { resource ->
            if (resource != null) {
                when (resource.status) {
                    Status.LOADING -> {
                        mTvShowViewModel.setLoading(true)
                    }

                    Status.SUCCESS -> {
                        mFilmAdapter.addFilms(resource.data)
                        mTvShowViewModel.setLoading(false)
                    }

                    Status.ERROR -> {
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT)
                            .show()
                        mTvShowViewModel.setLoading(false)
                    }
                }

            }
        })
    }
}
