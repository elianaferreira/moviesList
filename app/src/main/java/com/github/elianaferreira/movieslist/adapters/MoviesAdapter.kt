package com.github.elianaferreira.movieslist.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.movieslist.models.Movie
import com.github.elianaferreira.movieslist.utils.Utils
import com.github.elianaferreira.viewholder.GenericViewHolder
import com.squareup.picasso.Picasso

class MoviesAdapter(private val dataSet: List<Movie>, private val callback: (Movie) -> Unit): RecyclerView.Adapter<GenericViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return GenericViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        val movie = dataSet[position]
        val title = if (movie.title != null && movie.title.isNotEmpty()) movie.title else movie.name
        holder.get(R.id.item_title, TextView::class.java).text = title
        val date = if (movie.releaseDate != null && movie.releaseDate.isNotEmpty()) movie.releaseDate else movie.firstAirDate
        holder.get(R.id.item_description, TextView::class.java).text = "Release: $date"
        val rate = movie.voteAverage
        val rateCount = movie.voteCount
        holder.get(R.id.item_rate, RatingBar::class.java).rating = rate.toFloat()
        holder.get(R.id.item_rate_value, TextView::class.java).text = "$rate ($rateCount)"

        Picasso.get()
            .load(Utils.getPosterURL(movie.posterPath))
            .placeholder(R.drawable.img_film)
            .error(R.drawable.img_film)
            .into(holder.get(R.id.img_cinema, ImageView::class.java))

        holder.view.setOnClickListener(View.OnClickListener { callback(movie) })
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}