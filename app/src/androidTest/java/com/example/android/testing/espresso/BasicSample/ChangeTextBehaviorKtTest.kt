/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.testing.espresso.BasicSample

import android.app.Activity
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * The kotlin equivalent to the ChangeTextBehaviorTest, that
 * showcases simple view matchers and actions like [ViewMatchers.withId],
 * [ViewActions.click] and [ViewActions.typeText], and ActivityScenarioRule
 *
 *
 * Note that there is no need to tell Espresso that a view is in a different [Activity].
 */
@RunWith(AndroidJUnit4::class)
class ChangeTextBehaviorKtTest {

    /**
     * Use [ActivityScenarioRule] to create and launch the activity under test before each test,
     * and close it after each test. This is a replacement for
     * [androidx.test.rule.ActivityTestRule].
     */
    @Rule
    var activityScenarioRule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    @Test
    fun changeText_sameActivity() {
        // Type text for editTextUserInput and then press the button btnChangeText
        onView(withId(R.id.editTextUserInput)).perform(typeText("Thach Hong Phuong Thao"), closeSoftKeyboard())
        onView(withId(R.id.btnChangeText)).perform(click())

        // check text of editTextUserInput match with text of textToBeChanged
        onView(withId(R.id.textToBeChanged)).check(matches(withText("Thach Hong Phuong Thao")))

        // Check text of editTextUserInput with color is android.R.color.holo_red_dark
        onView(allOf(withId(R.id.editTextUserInput))).check(matches(hasTextColor(android.R.color.holo_red_dark)))
    }

    @Test
    fun changeText_newActivity() {
        // Type text and then press the button.
        onView(withId(R.id.editTextUserInput)).perform(typeText(STRING_TO_BE_TYPED),
                closeSoftKeyboard())
        onView(withId(R.id.activityChangeTextBtn)).perform(click())

        // This view is in a different Activity, no need to tell Espresso.
        onView(withId(R.id.show_text_view)).check(matches(withText("Thach Hong Phuong Thao")))
    }

    @Test
    fun getStyleText() {
        onView(withId(R.id.editTextUserInput)).perform(typeText(STRING_TO_BE_TYPED),
                closeSoftKeyboard())
        onView(allOf(withId(R.id.editTextUserInput))).check(matches(hasTextColor(android.R.color.holo_red_dark)))
    }

    @Test
    fun test_matchHint_editTextUserInput(){
        onView(withId(R.id.editTextUserInput)).check(matches(withHint("type something…")))

//        onView(withId(R.id.editTextUserInput)).check(matches(HintMatchers.withHint("type something…")))
    }

    @Test
    fun test_matchTextSize_editTextUserInput(){
        onView(withId(R.id.editTextUserInput)).check(matches(TextSizeMatchers.withTextSize(15F)))
    }

    @Test
    fun testLabelUpdatesIfValidCountrySelected() {
        // Click on the Spinner
        onView(withId(R.id.spinner_countries)).perform(click())

        // Select a country from the list
        onData(`is`<String>("France")).perform(click())

        // Check that the country label is updated with selected country
        onView(withId(R.id.textToBeChangedFromSpinner)).check(matches(withText("France")))
    }



    companion object {
        val STRING_TO_BE_TYPED = "Thach Hong Phuong Thao"
    }
}