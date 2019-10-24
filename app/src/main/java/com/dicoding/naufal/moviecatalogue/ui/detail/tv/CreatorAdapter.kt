package com.dicoding.naufal.moviecatalogue.ui.detail.tv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.naufal.moviecatalogue.R
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.CreatorEntity
import kotlinx.android.synthetic.main.item_creator.view.*

class CreatorAdapter(private val list: MutableList<CreatorEntity>) :
    RecyclerView.Adapter<CreatorAdapter.GenreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_creator, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun addItems(data: List<CreatorEntity>?) {
        list.clear()
        if (data?.isNotEmpty() == true) {
            list.addAll(data)
        }
        notifyDataSetChanged()
    }

    inner class GenreViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(creator: CreatorEntity) {
            view.txt_creator.text = creator.name
        }
    }
}