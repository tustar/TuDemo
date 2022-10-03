plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.squareup.kotlinpoet)
    implementation(libs.google.auto.service)
    implementation(libs.symbol.processing.api)
    implementation(project(":annotations:annotation"))
}