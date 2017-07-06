package com.settler.create

import com.settler.SettlerApplication
import com.settler.create.CreatePropertyContract.UiController
import reactor.core.publisher.Flux
import settler.com.reactor.CompositeDisposable
import javax.inject.Inject

class CreatePropertyPresenter : CreatePropertyContract.Presenter {

    @Inject lateinit var compositeDisposable: CompositeDisposable

    @Inject lateinit var validator: PropertyValidator

    private lateinit var uiController: UiController

    private var propertyNumberValid = false

    private var propertyAddressValid = false

    init {
        SettlerApplication.Companion.injector.inject(this)
    }

    override fun attach(uiController: UiController) {
        this.uiController = uiController
        setupPropertyNumberTextChangesFlux(uiController.getPropertyNumberChangesFlux())
        setupPropertyAddressTextChangesFlux(uiController.getPropertyAddressChangesFlux())
    }

    private fun setupPropertyNumberTextChangesFlux(flux: Flux<CharSequence>) {

        compositeDisposable.add(
                flux
                        .distinctUntilChanged()
                        .map(validator::validatePropertyNumber)
                        .doOnNext {
                            uiController.updatePropertyNumberMsg(it.messageResourceId)
                            propertyNumberValid = it.valid
                            refreshSaveButton()
                        }.subscribe()
        )

    }

    private fun setupPropertyAddressTextChangesFlux(flux: Flux<CharSequence>) {

        compositeDisposable.add(
                flux
                        .distinctUntilChanged()
                        .map(validator::validatePropertyAddress)
                        .doOnNext {
                            uiController.updatePropertyAddressMsg(it.messageResourceId)
                            propertyAddressValid = it.valid
                            refreshSaveButton()
                        }.subscribe()
        )
    }

    private fun refreshSaveButton() {
        uiController.updateSaveButtonStatus(propertyNumberValid.and(propertyAddressValid))
    }

    override fun cleanup() {
        compositeDisposable.clear()
    }

}
