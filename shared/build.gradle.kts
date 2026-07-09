import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true

            linkerOpts.add("-lsqlite3")
        }
    }

    js {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    androidLibrary {
        namespace = "com.kabindra.clean.architecture.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
        }
        androidResources {
            enable = true
        }
        withHostTest {
            isIncludeAndroidResources = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)

            implementation(libs.androidx.core.splashscreen)
            implementation(libs.androidx.browser)

            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.ktor.client.okhttp)

            implementation(libs.sqlite.bundled)

            implementation(project.dependencies.platform(libs.google.firebase.bom))
            implementation(libs.bundles.firebase)

            implementation(libs.inspektor)
        }
        commonMain.dependencies {
            implementation(projects.inAppUpdate)

            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.jetbrains.navigation3.ui)
            implementation(libs.jetbrains.material3.adaptiveNavigation3)
            implementation(libs.adaptive.navigation.suite)
            implementation(libs.jetbrains.lifecycle.viewmodelNavigation3)
            // implementation(libs.navigation3.browser)
            implementation(libs.androidx.startup.runtime)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)

            implementation(libs.bundles.koin)
            implementation(libs.bundles.ktor)
            implementation(libs.bundles.coil)

            // Room
            implementation(libs.room.runtime)
            implementation(libs.sqlite)

            // Third party libraries
            implementation(libs.bundles.compottie)
            implementation(libs.sdp.ssp)

            implementation(libs.haze)
            implementation(libs.haze.blur)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqlite.bundled)

            implementation(libs.bundles.firebase)

            implementation(libs.inspektor)
        }
        jsMain {
            dependencies {
                implementation(libs.wrappers.browser)
                implementation(libs.ktor.client.js)
                implementation(libs.sqlite.web)
                implementation(project((":sqliteWasmWorker")))
                implementation(project((":sqlJsWorker")))
            }
        }
        wasmJsMain {
            dependencies {
                implementation(libs.wrappers.browser)
                implementation(libs.ktor.client.js)
                implementation(libs.sqlite.web)
                implementation(project((":sqliteWasmWorker")))
                implementation(project((":sqlJsWorker")))
            }
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
    add("kspAndroid", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspJs", libs.room.compiler)
    add("kspWasmJs", libs.room.compiler)
}

room3 {
    schemaDirectory(layout.projectDirectory.dir("schemas"))
}