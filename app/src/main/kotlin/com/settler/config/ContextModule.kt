package com.settler.config

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.settler.create.CreatePropertyContract
import com.settler.create.CreatePropertyPresenter
import com.settler.create.PropertyValidator
import dagger.Module
import dagger.Provides
import settler.com.reactor.CompositeDisposable
import javax.inject.Singleton

@Module
open class ContextModule(val app : Application) {

    @Provides
    @Singleton
    open fun provideContext() : Context = app

    @Provides
    @Singleton
    open fun provideResources() : Resources = app.resources

    @Provides
    open fun provideNewPropertyValidator() = PropertyValidator()

    @Provides
    open fun provideCompositeDisposable() = CompositeDisposable()

    @Provides
    open fun provideCreatePropertyPresenter(): CreatePropertyContract.Presenter = CreatePropertyPresenter()

}