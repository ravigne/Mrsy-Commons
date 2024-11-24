package com.mrsy.commons.samples

import com.github.ajalt.reprint.core.Reprint
import com.mrsy.commons.RightApp
import com.mrsy.commons.helpers.rustore.RuStoreModule

class App : RightApp() {
    override fun onCreate() {
        super.onCreate()
        Reprint.initialize(this)
        RuStoreModule.install(this, "309929407")
    }
}
