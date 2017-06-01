package com.settler.config

import com.settler.create.NewPropertyActivity
import com.settler.create.NewPropertyValidator
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ContextModule::class))
interface Injector {

    fun inject(injected: NewPropertyValidator)
    fun inject(injected: NewPropertyActivity)

}