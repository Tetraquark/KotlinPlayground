package ru.tetraquark.kotlin.playground.app

import android.app.Application
import com.github.aakira.napier.DebugAntilog
import ru.tetraquark.kotlin.playground.shared.SharedFactory

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AppComponent.factory = SharedFactory(
            antilog = DebugAntilog(),
        )
    }
}
