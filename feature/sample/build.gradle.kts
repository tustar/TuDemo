plugins {
    id("tustar.android.feature")
    id("tustar.android.library.compose")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("app.cash.molecule")
    id("com.google.devtools.ksp")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    ndkVersion = libs.versions.ndk.get()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

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
        jvmTarget = libs.versions.jvmTarget.get()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
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
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.ext.compiler)
    // Accompanist
    implementation(libs.bundles.accompanist)
    // Compose
    implementation(libs.bundles.compose)
    implementation(libs.androidx.compose.ui.test)
    implementation(libs.androidx.compose.material.iconsExtended)
    // Test
    androidTestImplementation(libs.bundles.test)
    // annotations
    implementation(project(":annotations:annotation"))
    kapt(project(":annotations:compiler"))
//    ksp(project(":annotations:ksp"))
}