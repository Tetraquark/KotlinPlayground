plugins {
    plugin(Deps.Plugins.androidLibrary)
    plugin(Deps.Plugins.kotlinMultiplatform)
    plugin(Deps.Plugins.mobileMultiplatform)
    plugin(Deps.Plugins.iosFramework)
}

val mppLibs = listOf<MultiPlatformLibrary>()
val mppModules = listOf<MultiPlatformModule>()

kotlin {
    macosX64 {
        compilations["main"].cinterops {
            val libmultiplier by creating {
                includeDirs(
                    "${projectDir.absolutePath}/build/cinterop"
                )
                // About cinterop and static C libraries with relative paths
                // https://github.com/JetBrains/kotlin-native/issues/2314
                extraOpts(
                    "-libraryPath",
                    "${projectDir.absolutePath}/build/cinterop"
                )
            }
        }
    }
    sourceSets {
        val commonMain by getting
        val desktopMain by creating {
            dependsOn(commonMain)
        }
        val macosX64Main by getting {
            dependsOn(desktopMain)
        }
//        val linuxX64Main by getting {
//            dependsOn(desktopMain)
//        }
//        val mingwX64Main by getting {
//            dependsOn(desktopMain)
//        }
    }
}


dependencies {
    commonMainApi(Deps.Libs.MultiPlatform.coroutines)

    androidMainImplementation(Deps.Libs.Android.lifecycle)

    mppLibs.forEach { commonMainApi(it.common) }
    mppModules.forEach { commonMainApi(project(it.name)) }

    // org.gradle.internal.resolve.ArtifactNotFoundException: Could not find test-core-0.3.0-samplessources.jar
    //commonTestImplementation(Deps.Libs.MultiPlatform.Tests.mokoTestCore)
}

framework {
    mppModules.forEach { export(it) }
    mppLibs.forEach { export(it) }
}
