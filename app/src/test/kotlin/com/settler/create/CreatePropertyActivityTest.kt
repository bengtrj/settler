package com.settler.create

import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.settler.BuildConfig
import com.settler.config.CustomRobolectricTestRunner
import com.settler.config.MockSettlerApplication
import kotlinx.android.synthetic.main.content_new_property.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(CustomRobolectricTestRunner::class)
@Config(constants = BuildConfig::class,
        application = MockSettlerApplication::class,
        sdk = intArrayOf(25))
class CreatePropertyActivityTest {

    private lateinit var activity: CreatePropertyActivity
    private lateinit var presenter: CreatePropertyContract.Presenter
    private lateinit var activityController: ActivityController<CreatePropertyActivity>

    @Before
    fun setup() {

        activityController = Robolectric.buildActivity(CreatePropertyActivity::class.java)
        activity = activityController.get()

        activityController.create()

    }

    @Test
    fun shouldInitializePresenterUiControllerOnCreation() {
        verify(presenter, times(1)).attach(activity)
    }

    @Test
    fun shouldEnableSaveButton() {
        activityController.start()
        activity.updateSaveButtonStatus(true)
        assertTrue(activity.saveButton.isEnabled)
    }

    @Test
    fun shouldDisableSaveButton() {
        activityController.start()
        activity.updateSaveButtonStatus(false)
        assertFalse(activity.saveButton.isEnabled)
    }

}
