package com.settler.config

import com.settler.create.CreatePropertyActivity
import com.settler.create.CreatePropertyContract
import com.settler.create.CreatePropertyPresenter
import com.settler.create.PropertyValidator
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ContextModule::class))
interface Injector {

    fun inject(injected: PropertyValidator)
    fun inject(injected: CreatePropertyActivity)
    fun inject(injected: CreatePropertyPresenter)

}