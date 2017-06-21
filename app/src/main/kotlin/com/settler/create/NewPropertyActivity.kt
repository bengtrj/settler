package com.settler.create

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.jakewharton.rxbinding2.widget.RxTextView
import com.settler.R
import com.settler.SettlerApplication
import com.settler.ValidationResult
import io.reactivex.Observable
import io.reactivex.Observable.combineLatest
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.content_new_property.*
import javax.inject.Inject

class NewPropertyActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    @Inject lateinit var validator: NewPropertyValidator

    init {
        SettlerApplication.Companion.injector.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_property)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        setupValidation()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun setupValidation() {
        propertyAddressDecorator.isErrorEnabled = true

        val propertyNumberObservable =
                setupFieldValidationObservable(propertyNumberInput, propertyNumberDecorator, validator::validatePropertyNumber)

        val propertyAddressObservable =
                setupFieldValidationObservable(propertyAddressInput, propertyAddressDecorator, validator::validatePropertyAddress)

        compositeDisposable.add(propertyNumberObservable.subscribe(handleValidation))
        compositeDisposable.add(propertyAddressObservable.subscribe(handleValidation))

        val isSignInEnabled: Observable<Boolean> = combineLatest(
                propertyNumberObservable,
                propertyAddressObservable,
                BiFunction {
                    numberValid, addressValid ->
                    numberValid.second.valid && addressValid.second.valid
                }
        )

        isSignInEnabled
                .distinctUntilChanged()
                .subscribe({ valid -> saveButton.isEnabled = valid })

    }

    private fun setupFieldValidationObservable(input: TextInputEditText,
                                               inputDecorator: TextInputLayout,
                                               validationFunction: (CharSequence) -> ValidationResult
    ): Observable<Pair<TextInputLayout, ValidationResult>> {

        return RxTextView.textChanges(input)
                .distinctUntilChanged()
                .map(validationFunction)
                .map {
                    result ->
                    Pair(inputDecorator, result)
                }
    }

    fun validate(string: CharSequence) : ValidationResult = ValidationResult(true, null)

    private val handleValidation: (Pair<TextInputLayout, ValidationResult>) -> Unit = {
        (layout, result) ->
        layout.error = result.messageResourceId?.let { getString(result.messageResourceId) }
    }

}