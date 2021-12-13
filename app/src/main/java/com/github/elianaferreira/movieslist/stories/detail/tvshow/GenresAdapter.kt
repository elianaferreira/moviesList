package com.github.elianaferreira.movieslist.stories.detail.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.viewholder.GenericViewHolder

class GenresAdapter(private val dataSet: List<String>): RecyclerView.Adapter<GenericViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_genre, parent, false)
        return GenericViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.get(R.id.item_genre, TextView::class.java).text = dataSet[position]
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}