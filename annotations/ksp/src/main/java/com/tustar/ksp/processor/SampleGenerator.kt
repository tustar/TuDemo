package com.tustar.ksp.processor

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.tustar.ksp.entity.SampleInfo

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

        return TypeSpec.classBuilder("Sample")
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter("group", String::class)
                    .addParameter("item", String::class)
                    .addParameter("createdAt", String::class)
                    .addParameter("updatedAt", String::class)
                    .build()
            )
            .addProperty(
                PropertySpec.builder("group", String::class)
                    .initializer("group")
                    .build()
            )
            .addProperty(
                PropertySpec.builder("item", String::class)
                    .initializer("item")
                    .build()
            )
            .addProperty(
                PropertySpec.builder("createdAt", String::class)
                    .initializer("createdAt")
                    .build()
            )
            .addProperty(
                PropertySpec.builder("updatedAt", String::class)
                    .initializer("updatedAt")
                    .build()
            )
            .addType(companion)
            .build()
    }

    private fun <T> buildFun(
        infos: MutableList<T>,
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
        infos.forEach {
            builder.addStatement(block.invoke(it), tClazz)
        }
        builder.addStatement("return result.sortedByDescending(${SampleInfo.CLASS_NAME}::updatedAt)")
        return builder.build()
    }

}