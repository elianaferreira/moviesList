package com.github.elianaferreira.movieslist.stories.list

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test

import org.junit.runner.RunWith

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.CoreMatchers.not
import androidx.test.ext.junit.rules.activityScenarioRule


import com.github.elianaferreira.movieslist.R
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class ListActivityTest {

    @get:Rule var activityScenarioRule = activityScenarioRule<ListActivity>()

    @Test
    fun progressbarNotDisplayedAtBegin() {
        onView(withId(R.id.progress_bar))
            .check(matches(not(isDisplayed())))
    }
}