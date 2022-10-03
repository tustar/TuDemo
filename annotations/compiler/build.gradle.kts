plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.squareup.kotlinpoet)
    implementation(libs.alibaba.fastjson)
    implementation(libs.google.auto.service)
    kapt(libs.google.auto.service)

    implementation(project(":annotations:annotation"))
    implementation(project(":annotations:assist"))
}