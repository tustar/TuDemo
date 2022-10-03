package com.tustar.assist

import com.google.devtools.ksp.symbol.KSAnnotation
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import javax.lang.model.element.Element
import com.tustar.annotation.Sample

data class SampleInfo(
    val group: String,
    val name: String,
    val desc: String,
    val image: String,
    val createdAt: String,
    val updatedAt: String,
) {
    fun toFunSpec(): String {
        return "result += %T(group = \"$group\", " +
                "\nname = \"$name\", " +
                "\ndesc = \"$desc\", " +
                "\nimage = \"$image\", " +
                "\ncreatedAt = \"$createdAt\", " +
                "\nupdatedAt = \"$updatedAt\")"
    }

    object Columns {
        const val GROUP = "group"
        const val NAME = "name"
        const val DESC = "desc"
        const val IMAGE = "image"
        const val CREATED_AT = "createdAt"
        const val UPDATED_AT = "updatedAt"
    }

    companion object {
        const val PACKAGE_CODEGEN = "com.tustar.codegen"
        const val FUN_NAME = "generateSamples"
        const val FILE_NAME = "Sample"
        const val CLASS_NAME = "Sample"

        fun Element.toSampleInfo(): SampleInfo {
            val annotation = getAnnotation(Sample::class.java)
            val group = annotation.group
            val name = annotation.name
            val desc = annotation.desc
            val image = annotation.image
            val createdAt = annotation.createdAt
            val updatedAt = annotation.updatedAt
            return SampleInfo(group, name, desc, image, createdAt, updatedAt)
        }

        fun KSAnnotation.toSampleInfo(): SampleInfo {
            val group = find(Columns.GROUP)!!
            val name = find(Columns.NAME)!!
            val desc = find(Columns.DESC)!!
            val image = find(Columns.IMAGE)!!
            val createdAt = find(Columns.CREATED_AT)!!
            val updatedAt = find(Columns.UPDATED_AT)!!
            return SampleInfo(group, name, desc, image, createdAt, updatedAt)
        }

        private fun KSAnnotation.find(key: String) =
            arguments.find {
                it.name?.getShortName() == key
            }?.value?.toString()

        fun sampleSpec(companion: TypeSpec): TypeSpec {
            return TypeSpec.classBuilder(CLASS_NAME)
                .primaryConstructor(
                    FunSpec.constructorBuilder()
                        .addParameter(Columns.GROUP, String::class)
                        .addParameter(Columns.NAME, String::class)
                        .addParameter(Columns.DESC, String::class)
                        .addParameter(Columns.IMAGE, String::class)
                        .addParameter(Columns.CREATED_AT, String::class)
                        .addParameter(Columns.UPDATED_AT, String::class)
                        .build()
                )
                .addProperty(
                    PropertySpec.builder(Columns.GROUP, String::class)
                        .initializer(Columns.GROUP)
                        .build()
                )
                .addProperty(
                    PropertySpec.builder(Columns.NAME, String::class)
                        .initializer(Columns.NAME)
                        .build()
                )
                .addProperty(
                    PropertySpec.builder(Columns.DESC, String::class)
                        .initializer(Columns.DESC)
                        .build()
                )
                .addProperty(
                    PropertySpec.builder(Columns.IMAGE, String::class)
                        .initializer(Columns.IMAGE)
                        .build()
                )
                .addProperty(
                    PropertySpec.builder(Columns.CREATED_AT, String::class)
                        .initializer(Columns.CREATED_AT)
                        .build()
                )
                .addProperty(
                    PropertySpec.builder(Columns.UPDATED_AT, String::class)
                        .initializer(Columns.UPDATED_AT)
                        .build()
                )
                .addType(companion)
                .build()
        }
    }
}