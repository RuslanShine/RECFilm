package com.example.recfilm


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest2 {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest2() {
        val searchView = onView(
            allOf(
                withId(R.id.search_view),
                childAtPosition(
                    allOf(
                        withId(R.id.home_fragment_root),
                        childAtPosition(
                            withId(R.id.fragment_placeholder),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        searchView.perform(click())

        val searchAutoComplete = onView(
            allOf(
                withId(com.google.android.material.R.id.search_src_text),
                childAtPosition(
                    allOf(
                        withId(com.google.android.material.R.id.search_plate),
                        childAtPosition(
                            withId(com.google.android.material.R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        searchAutoComplete.perform(replaceText("the"), closeSoftKeyboard())

        val editText = onView(
            allOf(
                withId(com.google.android.material.R.id.search_src_text), withText("the"),
                withParent(
                    allOf(
                        withId(com.google.android.material.R.id.search_plate),
                        withParent(withId(com.google.android.material.R.id.search_edit_frame))
                    )
                ),
                isDisplayed()
            )
        )
        editText.check(matches(withText("the")))

        val textView = onView(
            allOf(
                withId(R.id.title), withText("The batman"),
                withParent(
                    allOf(
                        withId(R.id.item_container),
                        withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("The batman")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
