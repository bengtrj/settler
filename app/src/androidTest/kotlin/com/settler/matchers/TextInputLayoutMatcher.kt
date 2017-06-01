package com.settler.matchers

import android.support.design.widget.TextInputLayout
import android.view.View
import org.hamcrest.Matchers.`is`

object TextInputLayoutMatcher {

    private fun matchesError(stringMatcher: org.hamcrest.Matcher<String>): org.hamcrest.Matcher<View> {
        checkNotNull(stringMatcher)

        return object : android.support.test.espresso.matcher.BoundedMatcher<View, TextInputLayout>(android.support.design.widget.TextInputLayout::class.java) {
            internal var actualError = ""

            override fun describeTo(description: org.hamcrest.Description) {
                description.appendText("with error: ")
                stringMatcher.describeTo(description)
                description.appendText("But got: " + actualError)
            }

            override fun matchesSafely(textInputLayout: android.support.design.widget.TextInputLayout): Boolean {
                val error = textInputLayout.error
                if (error != null) {
                    actualError = error.toString()
                    return stringMatcher.matches(actualError)
                }
                return false
            }
        }
    }

    val inputLayoutWithError: (String) -> org.hamcrest.Matcher<View> = {
        error ->
        matchesError(`is`(error))
    }

}

