package com.example.EspressoAndroid;

import android.view.View;
import com.google.android.apps.common.testing.ui.espresso.NoMatchingViewException;
import com.google.android.apps.common.testing.ui.espresso.ViewAssertion;
import com.google.common.base.Optional;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.google.inject.internal.util.$Preconditions.checkArgument;
import static junit.framework.Assert.assertEquals;

public class ExtraViewAssertions {

    private ExtraViewAssertions() { }

    public static ViewAssertion isVisible() {
        return new ViewAssertion() {
            @Override
            public void check(Optional<View> viewOptional, Optional<NoMatchingViewException> noMatchingViewExceptionOptional) {
                checkArgument(viewOptional.isPresent());
                View view = viewOptional.get();
                assertEquals(VISIBLE, view.getVisibility());
            }
        };
    }

    public static ViewAssertion isNotVisible() {
        return new ViewAssertion() {
            @Override
            public void check(Optional<View> viewOptional, Optional<NoMatchingViewException> noMatchingViewExceptionOptional) {
                checkArgument(viewOptional.isPresent());
                View view = viewOptional.get();
                assertEquals(GONE, view.getVisibility());
            }
        };
    }
}
