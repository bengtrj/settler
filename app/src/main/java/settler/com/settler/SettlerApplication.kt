package settler.com.settler

import android.app.Application
import settler.com.settler.config.ContextModule
import settler.com.settler.config.DaggerInjector
import settler.com.settler.config.Injector

class SettlerApplication : Application() {

    companion object {
        lateinit var injector: Injector
    }

    override fun onCreate() {
        super.onCreate()
        injector = DaggerInjector.builder().contextModule(getContextModule()).build()
    }

    fun getContextModule() : ContextModule  = ContextModule(this)

}