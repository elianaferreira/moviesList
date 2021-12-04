package com.github.elianaferreira.movieslist.activities

import android.content.Intent
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
            Category("movie/popular", "Popular Movies"),
            Category("movie/top_rated", "Top Rate Movies"),
            Category("tv/popular", "Popular Seriess"),
            Category("tv/top_rated", "Top Rate Series")
        )

        val rvCategories: RecyclerView = findViewById(R.id.list_categories)
        rvCategories.layoutManager = GridLayoutManager(this, 2)
        rvCategories.adapter = CategoriesAdapter(categories) {
            categoryValue ->
            val intent = Intent(this@MainActivity, ListActivity::class.java)
            intent.putExtra(ListActivity.PARAM_LIST_TYPE, categoryValue)
            startActivity(intent)
        }
    }
}