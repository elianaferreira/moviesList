package com.github.elianaferreira.movieslist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.movieslist.adapters.CategoriesAdapter
import com.github.elianaferreira.movieslist.models.Category

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar : Toolbar = findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)

        val categories = listOf<Category>(
            Category("movies", "Popular Movies"),
            Category("topRateMovies", "Top Rate Movies"),
            Category("series", "Popular Seriess"),
            Category("topRateSeries", "Top Rate Series")
        )

        val rvCategories: RecyclerView = findViewById(R.id.list_categories)
        rvCategories.layoutManager = GridLayoutManager(this, 2)
        rvCategories.adapter = CategoriesAdapter(categories) {
            categoryValue -> Toast.makeText(this@MainActivity, categoryValue, Toast.LENGTH_LONG).show()
        }
    }
}