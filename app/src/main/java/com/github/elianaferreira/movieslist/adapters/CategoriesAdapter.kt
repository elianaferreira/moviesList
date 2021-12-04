package com.github.elianaferreira.movieslist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.movieslist.models.Category
import com.github.elianaferreira.viewholder.GenericViewHolder

class CategoriesAdapter(private val dataSet: List<Category>, private val callback: (Category) -> Unit) : RecyclerView.Adapter<GenericViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return GenericViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        val category: Category = dataSet[position]
        holder.get(R.id.item_title, TextView::class.java).text = category.categoryName
        holder.view.setOnClickListener(View.OnClickListener { callback(category) })
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}