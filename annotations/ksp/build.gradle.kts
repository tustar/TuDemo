plugins {
    id("java-library")
    id("kotlin")
    id("org.jetbrains.kotlin.jvm")
    id("com.google.devtools.ksp")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.squareup.kotlinpoet)
    implementation(libs.google.auto.service.annotations)
    implementation(libs.symbol.processing.api)
    ksp(libs.auto.service.ksp)

    implementation(project(":annotations:annotation"))
    implementation(project(":annotations:assist"))
}