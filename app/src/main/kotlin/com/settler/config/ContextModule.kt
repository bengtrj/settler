package com.settler.config

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.settler.create.NewPropertyValidator
import dagger.Module
import dagger.Provides
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
    open fun provideNewPropertyValidator() = NewPropertyValidator()

}