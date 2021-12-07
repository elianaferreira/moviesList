package com.github.elianaferreira.movieslist.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.movieslist.adapters.CategoriesAdapter
import com.github.elianaferreira.movieslist.models.Category
import com.github.elianaferreira.movieslist.utils.Utils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

        val txtGreting: TextView = findViewById(R.id.greeting)
        txtGreting.text = Utils.getGreeting()

        val categories = listOf<Category>(
            Category("movie/popular", "Popular Movies", R.drawable.img_movie_popular),
            Category("movie/top_rated", "Top Rate Movies", R.drawable.img_movie_top_rated),
            Category("tv/popular", "Popular Series", R.drawable.img_show_popular),
            Category("tv/top_rated", "Top Rate Series", R.drawable.img_show_top_rated)
        )

        val rvCategories: RecyclerView = findViewById(R.id.list_categories)
        rvCategories.layoutManager = GridLayoutManager(this, 2)
        rvCategories.adapter = CategoriesAdapter(this@MainActivity, categories) {
            categoryValue ->
            val intent = Intent(this@MainActivity, ListActivity::class.java)
            intent.putExtra(ListActivity.PARAM_LIST_TYPE, categoryValue)
            startActivity(intent)
        }
    }
}