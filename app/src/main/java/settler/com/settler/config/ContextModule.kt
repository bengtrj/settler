package settler.com.settler.config

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import settler.com.settler.create.NewPropertyValidator
import javax.inject.Singleton

@Module
open class ContextModule(val app : Application) {

    @Provides
    @Singleton
    fun provideContext() : Context = app

    @Provides
    @Singleton
    fun provideResources() : Resources = app.resources

    @Provides
    fun provideNewPropertyValidator() = NewPropertyValidator()

}