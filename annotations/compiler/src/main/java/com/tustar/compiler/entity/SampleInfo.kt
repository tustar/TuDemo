package com.tustar.compiler.entity

import com.tustar.annotation.Sample
import javax.lang.model.element.Element


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

        fun Element.toSampleInfo(): SampleInfo {
            val annotation = getAnnotation(Sample::class.java)
            val group = annotation.group
            val item = annotation.item
            val createdAt = annotation.createdAt
            val updatedAt = annotation.updatedAt
            return SampleInfo(group, item, createdAt, updatedAt)
        }
    }
}