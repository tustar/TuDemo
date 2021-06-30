/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tustar.demo.buildsrc

object Versions {
    const val ktLint = "0.40.0"
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.1.0-alpha02"
    const val ktLint = "com.pinterest:ktlint:${Versions.ktLint}"

    object Amap {
        const val location = "com.amap.api:location:5.1.0"
    }

    object Accompanist {
        private const val version = "0.12.0"
        const val coil = "com.google.accompanist:accompanist-coil:$version"
        const val insets = "com.google.accompanist:accompanist-insets:$version"
    }

    object Kotlin {
        private const val version = "1.5.10"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"

        object Coroutines {
            private const val version = "1.4.3"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }
    }

    object AndroidX {

        const val coreKtx = "androidx.core:core-ktx:1.3.2"
        const val appcompat = "androidx.appcompat:appcompat:1.3.0-beta01"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"
        const val annotation = "androidx.annotation:annotation:1.2.0"
        const val vectordrawable = "androidx.vectordrawable:vectordrawable:1.1.0"

        object Legacy {
            const val supportV4 = "androidx.legacy:legacy-support-v4:1.0.0"
        }

        object Activity {
            const val compose = "androidx.activity:activity-compose:1.3.0-alpha05"
            const val ktx = "androidx.activity:activity-ktx:1.3.0-alpha08"
        }

        object Fragment {
            const val ktx = "androidx.fragment:fragment-ktx:1.3.2"
        }

        object Navigation {
            const val compose = "androidx.navigation:navigation-compose:1.0.0-alpha09"

            private const val version = "2.3.5"
            const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
            const val dynamicFeaturesFragment =
                "androidx.navigation:navigation-dynamic-features-fragment:$version"
            const val gradlePlugin =
                "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
            const val testing = "androidx.navigation:navigation-testing:$version"
        }

        object ConstraintLayout {
            const val compose = "androidx.constraintlayout:constraintlayout-compose:1.0.0-alpha06"

            private const val version = "2.0.4"
            const val constraintlayout = "androidx.constraintlayout:constraintlayout:$version"
        }

        object Compose {
            const val snapshot = ""
            private const val version = "1.0.0-beta09"

            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:$version"
            const val material = "androidx.compose.material:material:$version"
            const val materialIconsExtended =
                "androidx.compose.material:material-icons-extended:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val layout = "androidx.compose.foundation:foundation-layout:$version"
            const val tooling = "androidx.compose.ui:ui-tooling:$version"
            const val animation = "androidx.compose.animation:animation:$version"
            const val ui = "androidx.compose.ui:ui:$version"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:$version"
        }

        object Lifecycle {
            const val extensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"

            private const val version = "2.3.1"

            object ViewModel {
                const val compose =
                    "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha03"
                const val ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            }

            object LiveData {
                const val ktx = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            }

            object Runtime {
                const val ktx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            }
        }

        object Work {
            private const val version = "2.6.0-alpha01"
            const val runtimeKtx = "androidx.work:work-runtime-ktx:$version"
            const val gcm = "androidx.work:work-gcm:$version"
            const val testing = "androidx.work:work-testing:$version"
        }

        object Test {
            private const val version = "1.2.0"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.2-rc01"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
        }
    }

    object Hilt {
        private const val version = "2.37"

        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val android = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-compiler:$version"
        const val work = "androidx.hilt:hilt-work:1.0.0"
        const val navigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0-alpha03"
        const val testing = "com.google.dagger:hilt-android-testing:$version"
    }

    object JUnit {
        private const val version = "4.13.2"
        const val junit = "junit:junit:$version"
    }

    object Squareup {
        const val kotlinpoet = "com.squareup:kotlinpoet:1.7.2"

        object Retrofit2 {
            private const val version = "2.9.0"
            const val retrofit = "com.squareup.retrofit2:retrofit:$version"
            const val converterJson = "com.squareup.retrofit2:converter-gson:$version"
        }

        object OkHttp3 {
            const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.2.2"
        }
    }

    object Alibaba {
        const val fastJson = "com.alibaba:fastjson:1.2.71"
    }

    object Google {
        const val autoService = "com.google.auto.service:auto-service:1.0"
        const val material = "com.google.android.material:material:1.3.0"

        object Maps {
            const val maps = "com.google.android.libraries.maps:maps:3.1.0-beta"
            const val ktx = "com.google.maps.android:maps-v3-ktx:2.2.0"
        }
    }
}

object Urls {
    const val mavenCentralSnapshotRepo = "https://oss.sonatype.org/content/repositories/snapshots/"
    const val composeSnapshotRepo = "https://androidx.dev/snapshots/builds/" +
            "${Libs.AndroidX.Compose.snapshot}/artifacts/repository/"
}
