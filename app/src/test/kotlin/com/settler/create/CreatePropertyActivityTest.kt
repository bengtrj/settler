package com.settler.create

import android.annotation.SuppressLint
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.settler.BuildConfig
import com.settler.config.MockSettlerApplication
import kotlinx.android.synthetic.main.content_new_property.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import reactor.core.publisher.Flux
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class,
        application = MockSettlerApplication::class,
        minSdk = 25)
@SuppressLint("SetTextI18n")
class CreatePropertyActivityTest {

    private lateinit var activity: CreatePropertyActivity
    private lateinit var presenter: CreatePropertyContract.Presenter
    private lateinit var activityController: ActivityController<CreatePropertyActivity>
    private lateinit var propertyNumberInput: TextInputEditText
    private lateinit var propertyNumberDecorator: TextInputLayout
    private lateinit var propertyAddressInput: TextInputEditText
    private lateinit var propertyAddressDecorator: TextInputLayout

    @Before
    fun setup() {

        activityController = Robolectric.buildActivity(CreatePropertyActivity::class.java)
        activity = activityController.get()

        activityController.create()

        presenter = activity.presenter
        propertyNumberInput = activity.propertyNumberInput
        propertyNumberInput.setText("")
        propertyNumberDecorator = activity.propertyNumberDecorator
        propertyAddressInput = activity.propertyAddressInput
        propertyAddressInput.setText("")
        propertyAddressDecorator = activity.propertyAddressDecorator

    }

    @Test
    fun shouldInitializePresenterUiControllerOnCreation() {
        verify(presenter, times(1)).attach(activity)
    }

    @Test
    fun shouldProvideFluxesToPresenterOnStart() {
        activityController.start()

        verify(presenter, times(1)).setupPropertyNumberTextChangesFlux(any<Flux<CharSequence>>())
        verify(presenter, times(1)).setupPropertyAddressTextChangesFlux(any<Flux<CharSequence>>())
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
