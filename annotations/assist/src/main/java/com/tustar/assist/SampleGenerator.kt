package com.tustar.assist

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

object SampleGenerator {

    fun buildFileSpec(items: MutableList<SampleInfo>): FileSpec {
        val packageName = SampleInfo.PACKAGE_CODEGEN
        val fileName = SampleInfo.FILE_NAME
        return FileSpec.builder(packageName, fileName)
            .addType(createSample(packageName, items))
            .addImport(SampleInfo.PACKAGE_CODEGEN, SampleInfo.CLASS_NAME)
            .build()
    }

    private fun buildFunSpec(packageName: String, items: MutableList<SampleInfo>): FunSpec {
        return buildFun(
            items,
            packageName,
            className = SampleInfo.CLASS_NAME,
            funName = SampleInfo.FUN_NAME
        ) {
            it.toFunSpec()
        }
    }

    private fun createSample(packageName: String, items: MutableList<SampleInfo>): TypeSpec {
        val companion = TypeSpec.companionObjectBuilder()
            .addFunction(buildFunSpec(packageName, items))
            .build()
        return SampleInfo.sampleSpec(companion)
    }

    private fun <T> buildFun(
        items: MutableList<T>,
        packageName: String,
        className: String,
        funName: String,
        block: (T) -> String
    ): FunSpec {
        val tClazz = ClassName(packageName, className)
        val ktList = ClassName("kotlin.collections", "List")
        val ktListOfT = ktList.parameterizedBy(tClazz)
        val ktArrayList = ClassName("kotlin.collections", "ArrayList")
        val ktArrayListOfT = ktArrayList.parameterizedBy(tClazz)
        val builder = FunSpec.builder(funName)
            .returns(ktListOfT)
            .addStatement("val result = %T()", ktArrayListOfT)
        items.forEach {
            builder.addStatement(block.invoke(it), tClazz)
        }
        builder.addStatement("return result.sortedByDescending(${SampleInfo.CLASS_NAME}::updatedAt)")
        return builder.build()
    }
}