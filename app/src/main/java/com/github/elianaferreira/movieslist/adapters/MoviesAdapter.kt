package com.github.elianaferreira.movieslist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.movieslist.models.Movie
import com.github.elianaferreira.viewholder.GenericViewHolder

class MoviesAdapter(private val dataSet: List<Movie>): RecyclerView.Adapter<GenericViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return GenericViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        val movie = dataSet[position]
        val title = if (movie.title != null && movie.title.isNotEmpty()) movie.title else movie.name
        holder.get(R.id.item_title, TextView::class.java).text = title
        val rate = movie.voteAverage
        //TODO this is showing 0.0 in every case
        holder.get(R.id.item_description, TextView::class.java).text = "Rate: $rate"
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}