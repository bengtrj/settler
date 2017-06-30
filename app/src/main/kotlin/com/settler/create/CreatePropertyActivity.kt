package com.settler.create

import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.StringRes
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import com.settler.R
import com.settler.SettlerApplication
import com.settler.create.CreatePropertyContract.Presenter
import kotlinx.android.synthetic.main.activity_new_property.*
import kotlinx.android.synthetic.main.content_new_property.*
import settler.com.reactor.EditTextFlux
import javax.inject.Inject

class CreatePropertyActivity : CreatePropertyContract.UiController, AppCompatActivity() {

    @Inject lateinit var presenter: Presenter

    init {
        SettlerApplication.Companion.injector.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_property)

        setSupportActionBar(toolbar)
        presenter.attach(this)
    }

    override fun onStart() {
        super.onStart()
        setupFormFields()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.cleanup()
    }

    override fun updatePropertyNumberMsg(@StringRes messageResourceId: Int) {
        handleValidationMessage(propertyNumberDecorator, messageResourceId)
    }

    override fun updatePropertyAddressMsg(@StringRes messageResourceId: Int) {
        handleValidationMessage(propertyAddressDecorator, messageResourceId)
    }

    override fun updateSaveButtonStatus(enabled: Boolean) {
        saveButton.isEnabled = enabled
    }

    private fun setupFormFields() {
        propertyAddressDecorator.isErrorEnabled = true

        presenter.setupPropertyNumberTextChangesFlux(EditTextFlux.textChanges(propertyNumberInput))
        presenter.setupPropertyAddressTextChangesFlux(EditTextFlux.textChanges(propertyAddressInput))

    }

    //TODO see if instead of null, an empty resource String would be better
    private fun handleValidationMessage(@NonNull layout: TextInputLayout, @StringRes messageResourceId: Int) {
        layout.error = if (messageResourceId == R.string.empty) null else getString(messageResourceId)
    }

}