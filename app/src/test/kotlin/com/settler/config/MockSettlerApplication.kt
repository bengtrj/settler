package com.settler.config

import com.settler.SettlerApplication

class MockSettlerApplication: SettlerApplication() {

    override fun getContextModule() : ContextModule = MockContextModule(this)

}