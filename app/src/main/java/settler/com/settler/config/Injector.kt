package settler.com.settler.config

import dagger.Component
import settler.com.settler.create.NewPropertyActivity
import settler.com.settler.create.NewPropertyValidator
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ContextModule::class))
interface Injector {

    fun inject(injected: NewPropertyValidator)
    fun inject(injected: NewPropertyActivity)

}