package com.settler.create

import android.support.annotation.StringRes
import reactor.core.publisher.Flux

interface CreatePropertyContract {

    interface UiController {

        fun updatePropertyNumberMsg(@StringRes messageResourceId: Int)

        fun updatePropertyAddressMsg(@StringRes messageResourceId: Int)

        fun updateSaveButtonStatus(enabled: Boolean)

        fun getPropertyNumberChangesFlux(): Flux<CharSequence>

        fun getPropertyAddressChangesFlux(): Flux<CharSequence>

    }

    interface Presenter {

        fun attach(uiController: UiController)

        fun cleanup()

    }

}