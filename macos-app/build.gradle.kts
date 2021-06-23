plugins {
    plugin(Deps.Plugins.kotlinMultiplatform)
}

group = "ru.tetraquark"
version = "1.0"

repositories {
    mavenCentral()
}

kotlin {
    val nativeEntryPoint = "ru.tetraquark.kotlin.playground.macos.main"

    macosX64 {
        binaries {
            executable {
                entryPoint = nativeEntryPoint
                freeCompilerArgs = listOf(
                    "-linker-options",
                    "-L${projectDir.absolutePath}/../mpp-library/build/cinterop"
                )
            }
        }
    }

    sourceSets {
        val macosX64Main by getting {
            dependencies {
                implementation(project(":mpp-library"))
            }
        }
    }
}
