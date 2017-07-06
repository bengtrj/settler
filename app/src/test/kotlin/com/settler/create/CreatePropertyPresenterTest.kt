package com.settler.create

import com.nhaarman.mockito_kotlin.*
import com.settler.R
import com.settler.SettlerApplication
import com.settler.ValidationResult
import com.settler.create.CreatePropertyContract.UiController
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import reactor.core.Disposable
import reactor.test.publisher.TestPublisher

class CreatePropertyPresenterTest {

    lateinit var presenter: CreatePropertyPresenter
    lateinit var uiController: UiController
    lateinit var propertyNumberFluxPublisher: TestPublisher<CharSequence>
    lateinit var propertyAddressFluxPublisher: TestPublisher<CharSequence>

    @Before
    fun setUp() {

        SettlerApplication.Companion.injector = mock()

        presenter = CreatePropertyPresenter()
        presenter.compositeDisposable = mock()
        presenter.validator = mock()

        uiController = mock()

        propertyNumberFluxPublisher = TestPublisher.create<CharSequence>()
        whenever(uiController.getPropertyNumberChangesFlux()).thenReturn(propertyNumberFluxPublisher.flux())

        propertyAddressFluxPublisher = TestPublisher.create<CharSequence>()
        whenever(uiController.getPropertyAddressChangesFlux()).thenReturn(propertyAddressFluxPublisher.flux())

        whenever(presenter.validator.validatePropertyNumber(argThat { isEmpty() })).thenReturn(ValidationResult(false, R.string.empty))
        whenever(presenter.validator.validatePropertyNumber(argThat { startsWith("1") })).thenReturn(ValidationResult(true, R.string.empty))
        whenever(presenter.validator.validatePropertyNumber(argThat { startsWith("6") })).thenReturn(ValidationResult(false, R.string.validation_property_number))

        whenever(presenter.validator.validatePropertyAddress(argThat { isEmpty() })).thenReturn(ValidationResult(false, R.string.empty))
        whenever(presenter.validator.validatePropertyAddress(argThat { startsWith("Valid") })).thenReturn(ValidationResult(true, R.string.empty))
        whenever(presenter.validator.validatePropertyAddress(argThat { startsWith("Invalid") })).thenReturn(ValidationResult(false, R.string.validation_property_address))

        presenter.attach(uiController)

    }

    @Test
    fun shouldBeAttached() {
        verify(uiController, times(1)).getPropertyNumberChangesFlux()
        verify(uiController, times(1)).getPropertyAddressChangesFlux()
        verify(presenter.compositeDisposable, times(2)).add(any<Disposable>())
    }

    @Test
    fun shouldUseEmptyErrorMsgWhenPropertyNumberFieldIsEmpty() {

        propertyNumberFluxPublisher.next("")
        verify(presenter.validator, times(1)).validatePropertyNumber("")
        verify(uiController, times(1)).updatePropertyNumberMsg(R.string.empty)

    }

    @Test
    fun shouldUseEmptyErrorMsgWhenPropertyNumberFieldIsValid() {

        propertyNumberFluxPublisher.next("19990")
        verify(presenter.validator, times(1)).validatePropertyNumber("19990")
        verify(uiController, times(1)).updatePropertyNumberMsg(R.string.empty)

    }

    @Test
    fun shouldShowErrorMsgWhenPropertyNumberFieldIsInvalid() {

        propertyNumberFluxPublisher.next("6000")
        verify(presenter.validator, times(1)).validatePropertyNumber("6000")
        verify(uiController, times(1)).updatePropertyNumberMsg(R.string.validation_property_number)

    }

    @Test
    fun shouldUseEmptyErrorMsgWhenPropertyAddressFieldIsEmpty() {

        propertyAddressFluxPublisher.next("")
        verify(presenter.validator, times(1)).validatePropertyAddress("")
        verify(uiController, times(1)).updatePropertyAddressMsg(R.string.empty)

    }

    @Test
    fun shouldUseEmptyErrorMsgWhenPropertyAddressFieldIsValid() {

        propertyAddressFluxPublisher.next("Valid address 2")
        verify(presenter.validator, times(1)).validatePropertyAddress("Valid address 2")
        verify(uiController, times(1)).updatePropertyAddressMsg(R.string.empty)

    }

    @Test
    fun shouldShowErrorMsgWhenPropertyAddressFieldIsInvalid() {

        propertyAddressFluxPublisher.next("Invalid address")
        verify(presenter.validator, times(1)).validatePropertyAddress("Invalid address")
        verify(uiController, times(1)).updatePropertyAddressMsg(R.string.validation_property_address)

    }

    @Test
    fun shouldDisableSaveButtonWhenAllFieldsAreInvalid() {

        propertyNumberFluxPublisher.next("6000")
        verify(uiController, times(1)).updateSaveButtonStatus(false)

        propertyAddressFluxPublisher.next("Invalid address")
        verify(uiController, times(2)).updateSaveButtonStatus(false)

    }

    @Test
    fun shouldDisableSaveButtonWhenNumberIsInvalid() {

        propertyNumberFluxPublisher.next("6000")
        verify(uiController, times(1)).updateSaveButtonStatus(false)

        propertyAddressFluxPublisher.next("Valid address")
        verify(uiController, times(2)).updateSaveButtonStatus(false)

    }

    @Test
    fun shouldDisableSaveButtonWhenAddressIsInvalid() {

        propertyNumberFluxPublisher.next("12345")
        verify(uiController, times(1)).updateSaveButtonStatus(false)

        propertyAddressFluxPublisher.next("Invalid address")
        verify(uiController, times(2)).updateSaveButtonStatus(false)

    }

    @Test
    fun shouldEnableSaveButtonWhenAllFieldsAreValid() {

        propertyNumberFluxPublisher.next("12345")
        verify(uiController, times(1)).updateSaveButtonStatus(false)

        propertyAddressFluxPublisher.next("Valid address")
        verify(uiController, times(1)).updateSaveButtonStatus(true)

    }

    @Test
    fun shouldCleanup() {
        presenter.cleanup()
        verify(presenter.compositeDisposable, times(1)).clear()
    }

}