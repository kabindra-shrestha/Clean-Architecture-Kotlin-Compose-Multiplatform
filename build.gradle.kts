plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidMultiplatformLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.firebaseCrashlytics) apply false
}

// Ensure Kotlin compiler accepts expect/actual classes/objects across the build.
// This adds the compiler flag to all Kotlin compile tasks (multiplatform and JVM).
// Use the new `compilerOptions` DSL (replaces the deprecated `kotlinOptions`).
tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).configureEach {
    // Use the newer compilerOptions DSL available in recent Kotlin Gradle plugins.
    // Add the expect/actual flag to the free compiler args.
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}
