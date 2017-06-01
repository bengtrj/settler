package com.settler

import android.app.Application
import com.settler.config.ContextModule
import com.settler.config.DaggerInjector
import com.settler.config.Injector

open class SettlerApplication : Application() {

    companion object {
        lateinit var injector: Injector
    }

    override fun onCreate() {
        super.onCreate()
        injector = DaggerInjector.builder().contextModule(getContextModule()).build()
    }

    open fun getContextModule() : ContextModule = ContextModule(this)

}