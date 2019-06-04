package com.example.android.testing.espresso.BasicSample;

import android.view.View;
import android.widget.EditText;

import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.hamcrest.Matchers.is;

public class TextSizeMatchers {
    static Matcher<View> withTextSize(final Float expectedSize) {
        return withTextSize(is(expectedSize));
    }

    static Matcher<View> withTextSize(final Matcher<Float> expectedSize) {
        checkNotNull(expectedSize);
        return new BoundedMatcher<View, EditText>(EditText.class) {

            @Override
            public boolean matchesSafely(EditText view) {
                float pixels = view.getTextSize();
                float actualSize = pixels / view.getResources().getDisplayMetrics().scaledDensity;
                return expectedSize.matches(actualSize);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with fontsize: ");
                expectedSize.describeTo(description);
            }
        };
    }
}