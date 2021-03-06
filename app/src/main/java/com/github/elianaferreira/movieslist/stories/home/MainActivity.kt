package com.github.elianaferreira.movieslist.stories.home

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.movieslist.stories.home.di.DaggerHomeComponent
import com.github.elianaferreira.movieslist.stories.home.di.HomeModule
import com.github.elianaferreira.movieslist.stories.list.ListActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HomeView {

    @Inject
    lateinit var homePresenter: HomePresenter

    private lateinit var txtGreeting: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerHomeComponent.builder()
            .homeModule(HomeModule(this))
            .build()
            .inject(this)

        setContentView(R.layout.activity_main)
        homePresenter.setView(this)

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

        txtGreeting = findViewById(R.id.greeting)
        homePresenter.setWelcomeMessage()
        homePresenter.loadCategories()
    }

    override fun showWelcomeMessage(message: Int) {
        txtGreeting.text = getString(message)
    }


    override fun showCategories(categories: List<Category>) {
        val rvCategories: RecyclerView = findViewById(R.id.list_categories)
        rvCategories.layoutManager = GridLayoutManager(this, 2)
        rvCategories.adapter = CategoriesAdapter(this@MainActivity, categories) {
                categoryValue, view -> homePresenter.categorySelected(categoryValue, view)
        }
    }


    override fun onCategorySelected(category: Category, view: View) {
        val intent = Intent(this@MainActivity, ListActivity::class.java)
        intent.putExtra(ListActivity.PARAM_LIST_TYPE, category)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this@MainActivity,
            view,
            getString(R.string.transition_name)
        )
        startActivity(intent, options.toBundle())
    }
}