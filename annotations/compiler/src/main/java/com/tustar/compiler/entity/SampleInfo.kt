package com.tustar.compiler.entity


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
    }
}