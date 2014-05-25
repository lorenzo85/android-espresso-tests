package com.example.EspressoAndroid;

import android.app.Application;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import com.google.inject.Binder;
import com.google.inject.Module;
import roboguice.config.DefaultRoboModule;

import static com.example.EspressoAndroid.ExtraViewAssertions.isNotVisible;
import static com.example.EspressoAndroid.ExtraViewAssertions.isVisible;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.doesNotExist;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static com.google.inject.Stage.DEVELOPMENT;
import static org.mockito.Mockito.*;
import static roboguice.RoboGuice.newDefaultRoboModule;
import static roboguice.RoboGuice.setBaseApplicationInjector;

@LargeTest
public class MyActivityTest extends ActivityInstrumentationTestCase2<MyActivity> {

    protected MyService service;

    private MyActivity activity;

    public MyActivityTest() {
        super(MyActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        service = mock(MyService.class);
        setUpInjections();
    }

    public void testOnClickTextChanged() {
        startActivity();

        onView(withId(R.id.button))
                .perform(click());

        onView(withId(R.id.testView1))
                .check(matches(withText("Yee")));
    }

    public void testTextIsFound() {
        startActivity();

        onView(withId(R.id.testView1))
                .check(matches(withText("Hello World, MyActivity")));
    }

    public void testView1IsVisible() {
        startActivity();

        onView(withId(R.id.testView1))
                .check(isVisible());
    }

    public void testView2IsNotVisible() {
        startActivity();

        onView(withId(R.id.testView2))
                .check(isNotVisible());
    }

    public void testOnButton2ClickedDialogIsOpen() {
        startActivity();

        onView(withId(R.id.button2))
                .perform(click());

        onView(withText("Alert!!"))
                .check(isVisible());
    }

    public void testOnResumeDialogIsHidden() throws Throwable {
        // Given
        startActivity();

        onView(withId(R.id.button2))
                .perform(click());

        // When
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                getInstrumentation().callActivityOnResume(activity);
            }
        });

        // Then
        onView(withText("Alert!!"))
                .check(doesNotExist());
    }

    public void testOnClickProperMessageIsShown() {
        // Given
        String expected = "aValue";
        when(service.doSomething(anyString())).thenReturn(expected);
        startActivity();

        // When
        onView(withId(R.id.button2))
                .perform(click());

        // Then
        onView(withText(expected))
                .check(isVisible());
    }

    private void startActivity() {
        activity = getActivity();
    }

    private void setUpInjections() {
        Application application = (Application) getInstrumentation()
                .getTargetContext().getApplicationContext();
        DefaultRoboModule module = newDefaultRoboModule(application);
        setBaseApplicationInjector(application, DEVELOPMENT, module, getMocksModule());
    }

    private Module getMocksModule() {
        return new Module() {
            @Override
            public void configure(Binder binder) {
                binder.bind(MyService.class).toInstance(service);
            }
        };
    }
}