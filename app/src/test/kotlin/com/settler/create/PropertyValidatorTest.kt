package com.settler.create

import com.settler.R
import com.settler.ValidationResult
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PropertyValidatorTest {

    lateinit var validator: PropertyValidator

    @Before
    fun setUp() {
        validator = PropertyValidator()
    }

    @Test
    fun propertyNumberShouldBeInvalidWhenInputEmpty() {
        val result = validator.validatePropertyNumber("")
        assertEquals(ValidationResult(false, R.string.empty), result)
    }

    @Test
    fun propertyNumberShouldBeValidWhenInputShorterThan6() {
        val result = validator.validatePropertyNumber("12345")
        assertEquals(ValidationResult(true, R.string.empty), result)
    }

    @Test
    fun propertyNumberShouldBeInvalidWhenInputLongerThan5() {
        val result = validator.validatePropertyNumber("123456")
        assertEquals(ValidationResult(false, R.string.validation_property_number), result)
    }

    @Test
    fun propertyAddressShouldBeInvalidWhenInputEmpty() {
        val result = validator.validatePropertyAddress("")
        assertEquals(ValidationResult(false, R.string.empty), result)
    }

    @Test
    fun propertyAddressShouldBeValidWhenInputShorterThan55() {
        val result = validator.validatePropertyAddress("50 Larkhill St., Dublin")
        assertEquals(ValidationResult(true, R.string.empty), result)
    }

    @Test
    fun propertyAddressShouldBeInvalidWhenInputLongerThan55() {
        val result = validator.validatePropertyAddress("A very unnecessary long address wih more than 55 chars!!")
        assertEquals(ValidationResult(false, R.string.validation_property_address), result)
    }

}