plugins {
    id ("tustar.android.library")
    id ("kotlin-kapt")
    id( "dagger.hilt.android.plugin")
    id ("kotlin-parcelize")
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
}

dependencies {
    // androidx
    implementation (libs.androidx.core.ktx)
    implementation (libs.androidx.appcompat)
    // Google
    implementation (libs.google.material)
    // Hilt
    implementation (libs.hilt.android)
    kapt( libs.hilt.compiler)
    kapt( libs.hilt.ext.compiler)
    // Retrofit
    implementation (libs.bundles.retrofit)
    implementation (libs.okhttp.logging.interceptor)
    // Paging
    implementation (libs.bundles.paging)
    // Test
    androidTestImplementation (libs.bundles.test)
}