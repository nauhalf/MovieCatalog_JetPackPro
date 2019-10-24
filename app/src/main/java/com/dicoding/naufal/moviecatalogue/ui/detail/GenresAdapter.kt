package com.dicoding.naufal.moviecatalogue.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.naufal.moviecatalogue.R
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.GenreEntity
import kotlinx.android.synthetic.main.item_genre.view.*

class GenresAdapter(private val list: MutableList<GenreEntity>) :
    RecyclerView.Adapter<GenresAdapter.GenreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun addItems(data: List<GenreEntity>?) {
        list.clear()
        if (data?.isNotEmpty() == true) {
            list.addAll(data)
        }
        notifyDataSetChanged()
    }

    inner class GenreViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(genre: GenreEntity) {
            view.txt_genre.text = genre.name
        }
    }
}