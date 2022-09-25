package com.tustar.ksp.entity

import com.google.devtools.ksp.symbol.KSAnnotation
import com.tustar.annotation.Sample


data class SampleInfo(
    val group: String,
    val item: String,
    val createdAt: String,
    val updatedAt: String,
) {
    fun toFunSpec(): String {
        return "result += %T(group = \"$group\", item = \"$item\", " +
                "createdAt = \"$createdAt\", updatedAt = \"$updatedAt\")"
    }

    companion object {
        const val PACKAGE_CODEGEN = "com.tustar.codegen"
        const val FUN_NAME = "generateSamples"
        const val FILE_NAME = "Sample"
        const val CLASS_NAME = "Sample"

        fun KSAnnotation.toSampleInfo(): SampleInfo {
            val group = find("group")!!
            val item = find("item")!!
            val createdAt = find("createdAt")!!
            val updatedAt = find("updatedAt")!!
            return SampleInfo(group, item, createdAt, updatedAt)
        }

        private fun KSAnnotation.find(key: String) =
            arguments.find {
                it.name?.getShortName() == key
            }?.value?.toString()
    }
}