package com.github.elianaferreira.movieslist.stories.home

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.movieslist.stories.list.ListActivity
import com.github.elianaferreira.viewholder.GenericViewHolder
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun exit() {
        Intents.release()
    }

    @Test
    fun goToListActivity() {
        Espresso.onView(ViewMatchers.withId(R.id.list_categories))
            .perform(RecyclerViewActions.actionOnItemAtPosition<GenericViewHolder>(0, ViewActions.click()))
        Intents.intended(IntentMatchers.hasComponent(ListActivity::class.java.name))
    }
}