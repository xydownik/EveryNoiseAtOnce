package com.example.everynoiseatonce

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.everynoiseatonce.presentation.activity.MainActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashActivityTest {

    @Test
    fun testAppLaunchesAndShowsStartButton() {
        launch(MainActivity::class.java)

        onView(withText("Let’s Start"))
            .check(matches(isDisplayed()))
    }
}
