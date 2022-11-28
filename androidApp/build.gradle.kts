plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.tikay.noteappkmm.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.tikay.noteappkmm.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    compileOptions{
        isCoreLibraryDesugaringEnabled = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))

//    val composeVersion = "1.3.1"
    val composeVersion = "1.3.0"

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.foundation:foundation-layout:$composeVersion")
    implementation("androidx.compose.animation:animation:$composeVersion")
    implementation("androidx.compose.animation:animation-core:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.compose.ui:ui-viewbinding:$composeVersion")
    implementation("androidx.compose.runtime:runtime:$composeVersion")
    implementation("androidx.compose.material3:material3:1.0.0-beta01")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.activity:activity-compose:1.6.1")

    implementation("androidx.compose.compiler:compiler:$composeVersion")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    // navigation
    implementation("androidx.navigation:navigation-compose:2.5.3")

    // lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.0-alpha01")

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("androidx.hilt:hilt-common:1.0.0")
    implementation("io.coil-kt:coil-compose:1.4.0")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

    val hiltVersion = "2.43.2"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    testImplementation("com.google.dagger:hilt-compiler:$hiltVersion")
    kaptTest("com.google.dagger:hilt-compiler:$hiltVersion")

}

kapt {
    correctErrorTypes = true
}

hilt {
    enableAggregatingTask = true
}