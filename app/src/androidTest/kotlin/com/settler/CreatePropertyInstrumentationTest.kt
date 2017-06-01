package com.settler

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.SmallTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.settler.create.NewPropertyActivity
import com.settler.matchers.TextInputLayoutMatcher.inputLayoutWithError
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class CreatePropertyInstrumentationTest {

    @Rule
    val rule = ActivityTestRule<NewPropertyActivity>(NewPropertyActivity::class.java)

    @Test
    @Throws(Exception::class)
    fun useAppContext() {

        onView(withId(R.id.propertyNumberDecorator)).check(matches(inputLayoutWithError("")))

        onView(withId(R.id.propertyNumberInput))
                .perform(typeText("12345"))
                .check(matches(isDisplayed()))

    }
}
