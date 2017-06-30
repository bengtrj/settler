package com.settler.create

import android.util.Log
import com.settler.R
import com.settler.ValidationResult

open class PropertyValidator {

    open fun validatePropertyNumber(input: CharSequence): ValidationResult {
        val empty = input.isEmpty()
        val shouldEnableSave = !empty && input.length <= 5
        val messageResourceId = if (empty || shouldEnableSave) R.string.empty else R.string.validation_property_number
        return ValidationResult(shouldEnableSave, messageResourceId)
    }

    open fun validatePropertyAddress(input: CharSequence): ValidationResult {
        val empty = input.isEmpty()
        val shouldEnableSave = !empty && input.length < 55
        val messageResourceId = if (empty || shouldEnableSave) R.string.empty else R.string.validation_property_address
        Log.d("validation", input.toString() + " empty " + empty + " shouldEnableSave " + shouldEnableSave + " messageResourceId " + messageResourceId)
        return ValidationResult(shouldEnableSave, messageResourceId)
    }

}