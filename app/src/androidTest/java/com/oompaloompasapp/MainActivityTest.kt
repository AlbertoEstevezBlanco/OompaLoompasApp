package com.oompaloompasapp

import android.app.Instrumentation
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onChildren
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.oompaloompasapp.MainActivity
import com.example.oompaloompasapp.oompaloompa.domain.Favorite
import com.example.oompaloompasapp.oompaloompa.domain.OompaLoompa
import com.example.oompaloompasapp.oompaloompa.domain.OompaLoompasRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito.`when`
import javax.inject.Inject
import kotlin.properties.Delegates
import com.example.oompaloompasapp.R
import com.example.oompaloompasapp.oompaloompa.domain.OompaLoompaException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: OompaLoompasRepository

    private lateinit var instrumentation: Instrumentation

    companion object {
        const val ANY_LOADING_TIME: Long = 5000
        const val ANY_NUMBER_OF_OOMPA_LOOMPAS: Int = 10
        const val ANY_ERROR_MSG: String = "No network connection"
    }

    @Before
    fun setup() {
        hiltRule.inject()
        instrumentation = InstrumentationRegistry.getInstrumentation()
    }

    @After
    fun tearDown() {

    }

    @Test
    fun shouldShowLoadingAnimationWhileNoOompaLoompasAreShown() = runTest {
        val loadingTimes = LoadingTimes(minimumLoadTime = ANY_LOADING_TIME)
        givenOompaLoompasCustomLoadingTimes(loadingTimes)

        ActivityScenario.launch(MainActivity::class.java)
        composeTestRule.waitForIdle()
        composeTestRule.onNode(hasText("Loading")).assertIsDisplayed()

        composeTestRule.waitUntil(timeoutMillis = ANY_LOADING_TIME) {
            composeTestRule.onAllNodesWithText("Loading").fetchSemanticsNodes().isEmpty()
        }
        loadingTimes.loadEnd = System.currentTimeMillis()

        assert(loadingTimes.actualLoadTime in ANY_LOADING_TIME..ANY_LOADING_TIME + 1000)
    }

    private data class LoadingTimes(val minimumLoadTime: Long) {
        var loadStart by Delegates.notNull<Long>()
        var loadEnd by Delegates.notNull<Long>()

        val actualLoadTime
            get() = loadEnd - loadStart
    }

    private suspend fun givenOompaLoompasCustomLoadingTimes(loadingTimes: LoadingTimes) {
        `when`(repository.getOompaLoompas()).then {
            loadingTimes.loadStart = System.currentTimeMillis()
            Thread.sleep(loadingTimes.minimumLoadTime)
            return@then emptyList<OompaLoompa>()
        }

    }

    @Test
    fun shouldShowOompaLoompasNameForLoadedOnes() = runTest {
        val oompaLoompas = givenThereAreSomeOompaLoompas(ANY_NUMBER_OF_OOMPA_LOOMPAS)

        ActivityScenario.launch(MainActivity::class.java)
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithText("Loading").fetchSemanticsNodes().isEmpty()
        }

        oompaLoompas.forEach { oompaLoompa ->
            composeTestRule.onNode(hasTestTag("OompaLoompas"))
                .assert(hasAnyDescendant(hasText(oompaLoompa.first_name)))
        }
    }

    private suspend fun givenThereAreSomeOompaLoompas(numberOfOompaLoompas: Int): List<OompaLoompa> {
        val oompaLoompas = (0 until numberOfOompaLoompas).map {
            OompaLoompa(
                id = "id_$it",
                age = 0,
                first_name = "first_name_$it",
                last_name = "last_name_$it",
                country = "country_$it",
                description = "description_$it",
                email = "email_$it",
                favorite = Favorite(color = "", food = "", random_string = "", song = ""),
                gender = "gender_$it",
                height = 0,
                image = "image_$it",
                profession = "profession_$it",
                quota = "quota_$it"
            )
        }
        `when`(repository.getOompaLoompas()).thenReturn(Result.success(oompaLoompas))
        return oompaLoompas
    }

    @Test
    fun shouldShowNoOompaLoompasMessageOnEmptyCase() = runTest {
        val noOompaLoompasMsg = instrumentation.targetContext.getString(R.string.no_oompa_loompas_message)
        givenThereAreNoOoompaLoompasAvailable()

        ActivityScenario.launch(MainActivity::class.java)
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithText("Loading").fetchSemanticsNodes().isEmpty()
        }

        composeTestRule.onNode(hasText(noOompaLoompasMsg)).assertIsDisplayed()
    }

    private suspend fun givenThereAreNoOoompaLoompasAvailable() {
        `when`(repository.getOompaLoompas()).thenReturn(Result.success(emptyList()))
    }

    @Test
    fun shouldShowErrorMsgOnErrorWhenLoadingOompaLoompas() = runTest {
        givenTheresAnErrorWhenLoadingOompaLoompas(ANY_ERROR_MSG)

        ActivityScenario.launch(MainActivity::class.java)
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithText("Loading").fetchSemanticsNodes().isEmpty()
        }

        composeTestRule.onNode(hasText(ANY_ERROR_MSG)).assertIsDisplayed()
    }

    private suspend fun givenTheresAnErrorWhenLoadingOompaLoompas(error: String) {
        `when`(repository.getOompaLoompas()).thenReturn(Result.failure(OompaLoompaException.GetAllException(error)))

    }
}