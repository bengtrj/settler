package com.settler.create

import android.content.res.Resources
import com.settler.R
import com.settler.SettlerApplication.Companion.injector
import com.settler.ValidationResult
import javax.inject.Inject

open class NewPropertyValidator {

    @Inject
    lateinit var resources: Resources

    init {
        injector.inject(this)
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