package com.github.elianaferreira.movieslist.stories.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.movieslist.utils.Utils
import com.github.elianaferreira.viewholder.GenericViewHolder
import com.squareup.picasso.Picasso

class MoviesAdapter(private val isForMovies: Boolean, private val dataSet: List<Movie>, private val callback: (Movie) -> Unit): RecyclerView.Adapter<GenericViewHolder>(), Filterable {

    private var filteredList: List<Movie> = dataSet

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return GenericViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        val movie = filteredList[position]
        val title = if (isForMovies) movie.title else movie.name
        holder.get(R.id.item_title, TextView::class.java).text = title
        val date = if (isForMovies) movie.releaseDate else movie.firstAirDate
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
        return filteredList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val searchValue = p0.toString()
                filteredList = if (searchValue.isEmpty()) {
                    dataSet
                } else {
                    dataSet.filter { if (isForMovies) it.title.lowercase().contains(searchValue.lowercase()) else it.name.lowercase().contains(searchValue.lowercase()) }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }


            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
               filteredList = p1?.values as List<Movie>
                notifyDataSetChanged()
            }

        }
    }


    fun addData(moreMovies: List<Movie>) {
        val mutableList = filteredList.toMutableList()
        mutableList.addAll(moreMovies)
        filteredList = mutableList.toList()
        notifyDataSetChanged()
    }

}