package com.settler.config

import android.app.Application
import com.nhaarman.mockito_kotlin.mock
import com.settler.create.NewPropertyValidator
import dagger.Module
import dagger.Provides

@Module
open class MockContextModule(app: Application) : ContextModule(app) {

    @Provides
    override fun provideNewPropertyValidator() : NewPropertyValidator = mock()

}