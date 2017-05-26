package settler.com.settler.create

import android.annotation.SuppressLint
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import kotlinx.android.synthetic.main.content_new_property.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import settler.com.settler.BuildConfig
import settler.com.settler.SettlerApplication

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class,
        application = SettlerApplication::class)
@SuppressLint("SetTextI18n")
class NewPropertyActivityTest {

    private lateinit var activity: NewPropertyActivity
    private lateinit var activityController: ActivityController<NewPropertyActivity>
    private lateinit var propertyNumberInput: TextInputEditText
    private lateinit var propertyNumberDecorator: TextInputLayout
    private lateinit var propertyAddressInput: TextInputEditText
    private lateinit var propertyAddressDecorator: TextInputLayout

    @Before
    fun setup() {
        activityController = Robolectric.buildActivity(NewPropertyActivity::class.java)
        activity = activityController.create().get()

        propertyNumberInput = activity.propertyNumberInput
        propertyNumberDecorator = activity.propertyNumberDecorator
        propertyAddressInput = activity.propertyAddressInput
        propertyAddressDecorator = activity.propertyAddressDecorator
    }


    @Test
    fun shouldValidateAddressNumber() {
        activityController.start()

        assertEquals(null, propertyNumberDecorator.error)

        propertyNumberInput.setText("12345")
        assertEquals(null, propertyNumberDecorator.error)

        propertyNumberInput.setText("123456")
        assertEquals("Invalid property number", propertyNumberDecorator.error)
    }

    @Test
    fun shouldValidateAddressStreet() {
        activityController.start()

        assertEquals(null, propertyAddressDecorator.error)

        propertyAddressInput.setText("50 Larkhill Road")
        assertEquals(null, propertyAddressDecorator.error)

        propertyAddressInput.setText("50 Larkhill")
        assertEquals("Invalid address", propertyAddressDecorator.error)

        propertyAddressInput.setText("50 Larkhill Road2")
        assertEquals(null, propertyAddressDecorator.error)

        propertyAddressInput.setText("50 Larkhill")
        assertEquals("Invalid address", propertyAddressDecorator.error)
    }

    @Test
    fun shouldEnableButtonIfAllFieldsAreValid() {
        activityController.start()

        assertFalse(activity.saveButton.isEnabled)

        propertyAddressInput.setText("50 Larkhill Road")
        assertFalse(activity.saveButton.isEnabled)

        propertyNumberInput.setText("12345")

        assertTrue(activity.saveButton.isEnabled)

    }

}
