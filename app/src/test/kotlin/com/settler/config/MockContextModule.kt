package com.settler.config

import android.app.Application
import com.nhaarman.mockito_kotlin.mock
import com.settler.create.CreatePropertyContract
import com.settler.create.CreatePropertyPresenter
import com.settler.create.PropertyValidator
import dagger.Module
import dagger.Provides
import settler.com.reactor.CompositeDisposable

@Module
open class MockContextModule(app: Application) : ContextModule(app) {

    @Provides
    override fun provideNewPropertyValidator() : PropertyValidator = mock()

    @Provides
    override fun provideCompositeDisposable(): CompositeDisposable = mock()

    @Provides
    override fun provideCreatePropertyPresenter(): CreatePropertyContract.Presenter = mock()

}