package com.settler.create

import com.settler.R
import com.settler.ValidationResult

open class NewPropertyValidator {

    open fun validatePropertyNumber(input: CharSequence): ValidationResult {
        val empty = input.isEmpty()
        val shouldEnableSave = !empty && input.length <= 5
        val message: Int? = if (empty || shouldEnableSave) null else R.string.validation_property_number
        return ValidationResult(shouldEnableSave, message)
    }

    open fun validatePropertyAddress(input: CharSequence): ValidationResult {
        val empty = input.isEmpty()
        val shouldEnableSave = input.length > 15
        val message: Int? = if (empty || shouldEnableSave) null else R.string.validation_property_address
        return ValidationResult(shouldEnableSave, message)
    }

}