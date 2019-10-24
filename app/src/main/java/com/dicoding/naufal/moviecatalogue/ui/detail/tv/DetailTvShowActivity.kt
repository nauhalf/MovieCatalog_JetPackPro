package com.dicoding.naufal.moviecatalogue.ui.detail.tv

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
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.TvEntity
import com.dicoding.naufal.moviecatalogue.databinding.ActivityDetailTvShowBinding
import com.dicoding.naufal.moviecatalogue.ui.detail.GenreItemDecoration
import com.dicoding.naufal.moviecatalogue.ui.detail.GenresAdapter
import com.dicoding.naufal.moviecatalogue.ui.detail.ProductionCompaniesAdapter
import com.dicoding.naufal.moviecatalogue.ui.detail.ProductionCompanyItemDecoration
import com.dicoding.naufal.moviecatalogue.utils.*
import com.google.android.material.snackbar.Snackbar
import com.xiaofeng.flowlayoutmanager.Alignment
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager
import kotlinx.android.synthetic.main.activity_detail_tv_show.*

class DetailTvShowActivity : BaseActivity<ActivityDetailTvShowBinding, DetailTvShowViewModel>() {

    private lateinit var mDetailTvShowViewModel: DetailTvShowViewModel
    private lateinit var mGenresAdapter: GenresAdapter
    private lateinit var mProductionCompaniesAdapter: ProductionCompaniesAdapter
    private lateinit var mCreatorAdapter: CreatorAdapter
    private lateinit var mDetailTvShowBinding: ActivityDetailTvShowBinding
    private var menu: Menu? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_detail_tv_show
    }

    override fun getViewModel(): DetailTvShowViewModel {
        mDetailTvShowViewModel =
            ViewModelProviders.of(this, ViewModelFactory.getInstance(application))
                .get(DetailTvShowViewModel::class.java)
        return mDetailTvShowViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.detailTvShowViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDetailTvShowBinding = getViewDataBinding()
        setUp()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            R.id.menu_favorite -> {
                if(mDetailTvShowViewModel.getIsFavorite().value == true){
                    mDetailTvShowViewModel.deleteToFavorite()
                    Snackbar.make(mDetailTvShowBinding.root, getString(R.string.remove_favorite), Snackbar.LENGTH_SHORT).show()
                } else {
                    mDetailTvShowViewModel.addToFavorite()
                    Snackbar.make(mDetailTvShowBinding.root, getString(R.string.add_favorite), Snackbar.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }


    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        mDetailTvShowViewModel.getIsFavorite().value?.let{
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
        mCreatorAdapter = CreatorAdapter(mutableListOf())

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
                LinearLayoutManager(this@DetailTvShowActivity, RecyclerView.HORIZONTAL, false)
            addItemDecoration(ProductionCompanyItemDecoration(12))
        }

        recycler_creator.apply {
            adapter = mCreatorAdapter
            layoutManager =
                LinearLayoutManager(this@DetailTvShowActivity, RecyclerView.HORIZONTAL, false)
            addItemDecoration(CreatorItemDecoration(15))
        }

        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        mDetailTvShowViewModel.setTvLiveData(intent.getIntExtra(TV_ID_EXTRA, 0))


        mDetailTvShowViewModel.getTvLiveData().observe(this, Observer { resource ->
            if (resource != null) {
                when (resource.status) {
                    Status.LOADING -> mDetailTvShowViewModel.setLoading(true)

                    Status.SUCCESS -> {
                        val it = resource.data as TvEntity
                        val date = it.firstAirDate?.let { dateString ->
                            getDateTimeFromString(dateString)
                        }
                        val runtime =
                            if (it.episodeRunTime != 0) {
                                getActualRuntime(it.episodeRunTime)
                            } else {
                                getActualRuntime(0)
                            }


                        txt_title.text = it.title
                        txt_year.text = resources.getString(R.string.year, date)
                        txt_rate.text = it.voteAverage.toString()
                        txt_overview.text = it.overview
                        txt_runtime.text =
                            resources.getString(
                                R.string.runtime,
                                runtime["h"],
                                runtime["m"]
                            )
                        txt_release.text = resources.getString(R.string.date_format, date)
                        txt_language.apply {
                            text = resources.getString(getLanguageReference(it.originalLanguage))

                            setCompoundDrawablesWithIntrinsicBounds(
                                getNationFlag(it.originalLanguage),
                                0,
                                0,
                                0
                            )
                        }
                        txt_original_title.text = it.originalName
                        txt_status.text = it.status

                        if (it.homepage.isNullOrEmpty()) {
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
                                intentBuilder.build().launchUrl(this, Uri.parse(it.homepage));
                            }
                        }


                        Glide.with(this)
                            .load(MovieUtils.getImagePoster(it.posterPath))
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
                            .load(MovieUtils.getImagePoster(it.backdropPath))
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


                        if (it.productionCompanies?.size != 0 && it.productionCompanies != null) {
                            txt_production_company_title.visibility = View.VISIBLE
                            mProductionCompaniesAdapter.addItems(it.productionCompanies)
                        } else {
                            txt_production_company_title.visibility = View.GONE
                        }

                        if (it.creators?.size != 0 && it.creators != null) {
                            txt_creator_title.visibility = View.VISIBLE
                            mCreatorAdapter.addItems(it.creators)
                        } else {
                            txt_creator_title.visibility = View.GONE
                        }

                        it.genres?.let { genre ->
                            mGenresAdapter.addItems(genre)
                        }
                        mDetailTvShowViewModel.setLoading(false)
                    }

                    Status.ERROR -> {
                        Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                        mDetailTvShowViewModel.setLoading(false)
                    }
                }

            }
        })

        mDetailTvShowViewModel.isLoading().observe(this, Observer {
            if (it) {
                progress_poster.visibility = View.VISIBLE
                progress_backdrop.visibility = View.VISIBLE
            }
        })

        mDetailTvShowViewModel.getIsFavorite().observe(this, Observer {
            if (it == true) {
                menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white)
            } else {
                menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white)
            }
        })
    }

    companion object {
        const val TV_ID_EXTRA = "TV_ID_EXTRA"
        fun newIntent(context: Context, tvId: Int): Intent =
            Intent(context, DetailTvShowActivity::class.java).apply {
                putExtra(TV_ID_EXTRA, tvId)
            }
    }
}
