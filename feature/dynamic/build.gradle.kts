import org.jetbrains.kotlin.kapt3.base.Kapt.kapt
plugins {
    id("tustar.android.feature")
    id("tustar.android.library.compose")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.tustar.dynamic"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    // androidx
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
    // Google
    implementation(libs.google.material)
    // Hilt
    implementation(libs.hilt.android)
    implementation(project(mapOf("path" to ":core:common")))
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.ext.compiler)
    // Accompanist
    implementation(libs.bundles.accompanist)
    // Compose
    implementation(libs.bundles.compose)
    implementation(libs.androidx.compose.ui.test)
    // Test
    androidTestImplementation(libs.bundles.test)
}