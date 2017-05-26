package settler.com.settler.create

import android.content.res.Resources
import settler.com.settler.R
import settler.com.settler.SettlerApplication
import settler.com.settler.ValidationResult
import javax.inject.Inject

class NewPropertyValidator {

    @Inject
    lateinit var resources: Resources

    init {
        SettlerApplication.injector.inject(this)
    }

    val validatePropertyNumber: (CharSequence) -> ValidationResult = {
        input ->
        val empty = input.isEmpty()
        val shouldEnableSave = !empty && input.length <= 5
        val message: Int? = if (empty || shouldEnableSave) null else R.string.validation_property_number
        ValidationResult(shouldEnableSave, message)
    }

    val validatePropertyAddress: (CharSequence) -> ValidationResult = {
        input ->
        val empty = input.isEmpty()
        val shouldEnableSave = input.length > 15
        val message: Int? = if (empty || shouldEnableSave) null else R.string.validation_property_address
        ValidationResult(shouldEnableSave, message)
    }



}