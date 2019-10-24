package com.dicoding.naufal.moviecatalogue.ui.detail.movie

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dicoding.jetpack.nauhalf.academy.utils.vo.Status
import com.dicoding.naufal.moviecatalogue.BR
import com.dicoding.naufal.moviecatalogue.R
import com.dicoding.naufal.moviecatalogue.base.BaseActivity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.MovieEntity
import com.dicoding.naufal.moviecatalogue.databinding.ActivityDetailMovieBinding
import com.dicoding.naufal.moviecatalogue.ui.detail.GenreItemDecoration
import com.dicoding.naufal.moviecatalogue.ui.detail.GenresAdapter
import com.dicoding.naufal.moviecatalogue.ui.detail.ProductionCompaniesAdapter
import com.dicoding.naufal.moviecatalogue.ui.detail.ProductionCompanyItemDecoration
import com.dicoding.naufal.moviecatalogue.utils.*
import com.google.android.material.snackbar.Snackbar
import com.xiaofeng.flowlayoutmanager.Alignment
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : BaseActivity<ActivityDetailMovieBinding, DetailMovieViewModel>() {

    private lateinit var mDetailMovieViewModel: DetailMovieViewModel
    private lateinit var mGenresAdapter: GenresAdapter
    private lateinit var mProductionCompaniesAdapter: ProductionCompaniesAdapter
    private lateinit var mDetailMovieBinding: ActivityDetailMovieBinding
    private var menu: Menu? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_detail_movie
    }

    override fun getViewModel(): DetailMovieViewModel {
        mDetailMovieViewModel =
            ViewModelProviders.of(this, ViewModelFactory.getInstance(application))
                .get(DetailMovieViewModel::class.java)
        return mDetailMovieViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.detailMovieViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDetailMovieBinding = getViewDataBinding()
        setUp()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            R.id.menu_favorite -> {
                if(mDetailMovieViewModel.getIsFavorite().value == true){
                    mDetailMovieViewModel.deleteToFavorite()
                    Snackbar.make(mDetailMovieBinding.root, getString(R.string.remove_favorite), Snackbar.LENGTH_SHORT).show()
                } else {
                    mDetailMovieViewModel.addToFavorite()
                    Snackbar.make(mDetailMovieBinding.root, getString(R.string.add_favorite), Snackbar.LENGTH_SHORT).show()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        mDetailMovieViewModel.getIsFavorite().value?.let{
            if(it){
                menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white)
            } else {
                menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white)
            }

        }
        return super.onPrepareOptionsMenu(menu)
    }



    private fun setUp() {

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        mGenresAdapter = GenresAdapter(mutableListOf())
        mProductionCompaniesAdapter = ProductionCompaniesAdapter(mutableListOf())

        recycler_genres.apply {
            adapter = mGenresAdapter
            layoutManager = FlowLayoutManager().apply {
                isAutoMeasureEnabled = true
                setAlignment(Alignment.LEFT)
            }
            addItemDecoration(GenreItemDecoration(5))
        }

        recycler_production_companies.apply {
            adapter = mProductionCompaniesAdapter
            layoutManager =
                LinearLayoutManager(this@DetailMovieActivity, RecyclerView.HORIZONTAL, false)
            addItemDecoration(ProductionCompanyItemDecoration(12))
        }

        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        mDetailMovieViewModel.setMovieLiveData(intent.getIntExtra(MOVIE_ID_EXTRA, 0))

        mDetailMovieViewModel.getMovieLiveData().observe(this, Observer {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> {
                        mDetailMovieViewModel.setLoading(true)
                    }

                    Status.SUCCESS -> {
                        val data = it.data as MovieEntity
                        val date = data.releaseDate?.let { dateString ->
                            getDateTimeFromString(dateString)
                        }
                        val runtime = getActualRuntime(data.runtime)

                        txt_title.text = data.title
                        txt_year.text = resources.getString(R.string.year, date)
                        txt_rate.text = data.voteAverage.toString()
                        txt_overview.text = data.overview
                        txt_runtime.text =
                            resources.getString(R.string.runtime, runtime["h"], runtime["m"])
                        txt_release.text = resources.getString(R.string.date_format, date)
                        txt_language.apply {
                            text = resources.getString(getLanguageReference(data.originalLanguage))

                            setCompoundDrawablesWithIntrinsicBounds(
                                getNationFlag(data.originalLanguage),
                                0,
                                0,
                                0
                            )
                        }
                        txt_original_title.text = data.originalTitle
                        txt_status.text = data.status

                        if (data.homepage.isNullOrEmpty()) {
                            txt_homepage.visibility = View.GONE
                        } else {
                            txt_homepage.visibility = View.VISIBLE
                            txt_homepage.setOnClickListener { _ ->

                                val intentBuilder = CustomTabsIntent.Builder()
                                intentBuilder.setToolbarColor(
                                    ContextCompat.getColor(
                                        this,
                                        R.color.colorPrimary
                                    )
                                )
                                intentBuilder.build().launchUrl(this, Uri.parse(data.homepage))
                            }
                        }

                        Glide.with(this)
                            .load(MovieUtils.getImagePoster(data.posterPath))
                            .listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    progress_poster.visibility = View.GONE;
                                    return false;
                                }

                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    progress_poster.visibility = View.GONE;
                                    return false;
                                }
                            })
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .centerCrop()
                            .into(img_poster)

                        Glide.with(this)
                            .load(MovieUtils.getImagePoster(data.backdropPath))
                            .listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    progress_backdrop.visibility = View.GONE;
                                    return false;
                                }

                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    progress_backdrop.visibility = View.GONE;
                                    return false;
                                }
                            })
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .centerCrop()
                            .into(img_backdrop)

                        if (data.productionCompanies?.size != 0 && data.productionCompanies != null) {
                            txt_production_company_title.visibility = View.VISIBLE
                            mProductionCompaniesAdapter.addItems(data.productionCompanies)
                        } else {
                            txt_production_company_title.visibility = View.GONE
                        }

                        data.genres?.let { genres ->
                            mGenresAdapter.addItems(genres)
                        }

                        mDetailMovieViewModel.setLoading(false)

                    }

                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        mDetailMovieViewModel.setLoading(false)
                    }
                }

            }
        })

        mDetailMovieViewModel.isLoading().observe(this, Observer {
            if (it) {
                progress_poster.visibility = View.VISIBLE
                progress_backdrop.visibility = View.VISIBLE
            }
        })

        mDetailMovieViewModel.getIsFavorite().observe(this, Observer {
            if (it == true) {
                menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white)
            } else {
                menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white)
            }
        })
    }

    companion object {
        const val MOVIE_ID_EXTRA = "MOVIE_ID"

        fun newIntent(context: Context, movieId: Int): Intent =
            Intent(context, DetailMovieActivity::class.java).apply {
                putExtra(MOVIE_ID_EXTRA, movieId)
            }
    }
}
