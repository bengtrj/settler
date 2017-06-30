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

    @Before
    fun setUp() {

        SettlerApplication.Companion.injector = mock()

        presenter = CreatePropertyPresenter()
        presenter.compositeDisposable = mock()
        presenter.validator = mock()

        uiController = mock()

        presenter.attach(uiController)

        verifyZeroInteractions(presenter.validator)
        verifyZeroInteractions(uiController)

        whenever(presenter.validator.validatePropertyNumber(argThat { isEmpty() })).thenReturn(ValidationResult(false, R.string.empty))
        whenever(presenter.validator.validatePropertyNumber(argThat { startsWith("1") })).thenReturn(ValidationResult(true, R.string.empty))
        whenever(presenter.validator.validatePropertyNumber(argThat { startsWith("6") })).thenReturn(ValidationResult(false, R.string.validation_property_number))

        whenever(presenter.validator.validatePropertyAddress(argThat { isEmpty() })).thenReturn(ValidationResult(false, R.string.empty))
        whenever(presenter.validator.validatePropertyAddress(argThat { startsWith("Valid") })).thenReturn(ValidationResult(true, R.string.empty))
        whenever(presenter.validator.validatePropertyAddress(argThat { startsWith("Invalid") })).thenReturn(ValidationResult(false, R.string.validation_property_address))
    }

    @Test
    fun shouldInitialize() {
        assertNotNull(uiController)
    }

    @Test
    fun shouldValidatePropertyNumber() {

        val mockFlux = TestPublisher.create<CharSequence>()
        presenter.setupPropertyNumberTextChangesFlux(mockFlux.flux())

        mockFlux.next("")
        verify(presenter.validator, times(1)).validatePropertyNumber("")
        verify(uiController, times(1)).updatePropertyNumberMsg(R.string.empty)

        mockFlux.next("6000")
        verify(presenter.validator, times(1)).validatePropertyNumber("6000")
        verify(uiController, times(1)).updatePropertyNumberMsg(R.string.validation_property_number)

        mockFlux.next("19990")
        verify(presenter.validator, times(1)).validatePropertyNumber("19990")
        verify(uiController, times(2)).updatePropertyNumberMsg(R.string.empty)

    }

    @Test
    fun shouldValidatePropertyAddress() {

        val mockFlux = TestPublisher.create<CharSequence>()
        presenter.setupPropertyAddressTextChangesFlux(mockFlux.flux())

        mockFlux.next("")
        verify(presenter.validator, times(1)).validatePropertyAddress("")
        verify(uiController, times(1)).updatePropertyAddressMsg(R.string.empty)

        mockFlux.next("Invalid address")
        verify(presenter.validator, times(1)).validatePropertyAddress("Invalid address")
        verify(uiController, times(1)).updatePropertyAddressMsg(R.string.validation_property_address)

        mockFlux.next("Valid address 2")
        verify(presenter.validator, times(1)).validatePropertyAddress("Valid address 2")
        verify(uiController, times(2)).updatePropertyAddressMsg(R.string.empty)

    }

    @Test
    fun shouldDisableSaveButtonWhenAllFieldsAreInvalid() {

        val mockPropertyNumberFlux = TestPublisher.create<CharSequence>()
        presenter.setupPropertyNumberTextChangesFlux(mockPropertyNumberFlux.flux())

        val mockPropertyAddressFlux = TestPublisher.create<CharSequence>()
        presenter.setupPropertyAddressTextChangesFlux(mockPropertyAddressFlux.flux())

        mockPropertyNumberFlux.next("6000")
        verify(uiController, times(1)).updateSaveButtonStatus(false)

        mockPropertyAddressFlux.next("Invalid address")
        verify(uiController, times(2)).updateSaveButtonStatus(false)

    }

    @Test
    fun shouldDisableSaveButtonWhenNumberIsInvalid() {

        val mockPropertyNumberFlux = TestPublisher.create<CharSequence>()
        presenter.setupPropertyNumberTextChangesFlux(mockPropertyNumberFlux.flux())

        val mockPropertyAddressFlux = TestPublisher.create<CharSequence>()
        presenter.setupPropertyAddressTextChangesFlux(mockPropertyAddressFlux.flux())

        mockPropertyNumberFlux.next("6000")
        verify(uiController, times(1)).updateSaveButtonStatus(false)

        mockPropertyAddressFlux.next("Valid address")
        verify(uiController, times(2)).updateSaveButtonStatus(false)

    }

    @Test
    fun shouldDisableSaveButtonWhenAddressIsInvalid() {

        val mockPropertyNumberFlux = TestPublisher.create<CharSequence>()
        presenter.setupPropertyNumberTextChangesFlux(mockPropertyNumberFlux.flux())

        val mockPropertyAddressFlux = TestPublisher.create<CharSequence>()
        presenter.setupPropertyAddressTextChangesFlux(mockPropertyAddressFlux.flux())

        mockPropertyNumberFlux.next("12345")
        verify(uiController, times(1)).updateSaveButtonStatus(false)

        mockPropertyAddressFlux.next("Invalid address")
        verify(uiController, times(2)).updateSaveButtonStatus(false)

    }

    @Test
    fun shouldEnableSaveButtonWhenAllFieldsAreValid() {

        val mockPropertyNumberFlux = TestPublisher.create<CharSequence>()
        presenter.setupPropertyNumberTextChangesFlux(mockPropertyNumberFlux.flux())

        val mockPropertyAddressFlux = TestPublisher.create<CharSequence>()
        presenter.setupPropertyAddressTextChangesFlux(mockPropertyAddressFlux.flux())

        mockPropertyNumberFlux.next("12345")
        verify(uiController, times(1)).updateSaveButtonStatus(false)

        mockPropertyAddressFlux.next("Valid address")
        verify(uiController, times(1)).updateSaveButtonStatus(true)

    }

    @Test
    fun shouldCleanup() {

        presenter.setupPropertyAddressTextChangesFlux(TestPublisher.create<CharSequence>().flux())
        verify(presenter.compositeDisposable, times(1)).add(any<Disposable>())


        presenter.setupPropertyNumberTextChangesFlux(TestPublisher.create<CharSequence>().flux())
        verify(presenter.compositeDisposable, times(2)).add(any<Disposable>())

        presenter.cleanup()
        verify(presenter.compositeDisposable, times(1)).clear()
    }

}