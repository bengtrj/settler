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
import reactor.core.publisher.Flux
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

        propertyAddressDecorator.isErrorEnabled = true
        presenter.attach(this)
    }

    override fun onStart() {
        super.onStart()
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

    override fun getPropertyNumberChangesFlux() = EditTextFlux.textChanges(propertyNumberInput)

    override fun getPropertyAddressChangesFlux() = EditTextFlux.textChanges(propertyAddressInput)

    private fun handleValidationMessage(@NonNull layout: TextInputLayout, @StringRes messageResourceId: Int) {
        layout.error = if (messageResourceId == R.string.empty) null else getString(messageResourceId)
    }

}