package com.oompaloompasapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.oompaloompasapp.oompaloompa.presenter.ui.screen.components.LoadingAnimation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoadingAnimationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun checkLoadingTextIsShown() {
        composeTestRule.setContent {
            LoadingAnimation()
        }

        composeTestRule.onNodeWithText("Loading").assertIsDisplayed()
    }

    @Test
    fun checkLoadingIndicatorIsShown() {
        composeTestRule.setContent {
            LoadingAnimation()
        }

        composeTestRule.onNodeWithTag("Loading Indicator").assertIsDisplayed()
    }
}