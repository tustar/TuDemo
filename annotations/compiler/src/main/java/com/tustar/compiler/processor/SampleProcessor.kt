package com.tustar.compiler.processor

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.tustar.annotation.Sample
import com.tustar.compiler.entity.SampleInfo
import javax.annotation.processing.*
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
class SampleProcessor : BaseProcessor() {

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        logger.info(">>> SampleProcessor init. <<<");
    }


    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val types: MutableSet<String> = LinkedHashSet()
        types.add(Sample::class.java.name)
        logger.info(">>> getSupportedAnnotationTypes <<<");
        return types
    }


    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment):
            Boolean {
        logger.info(">>> SampleProcessor process. <<<");
        val items = mutableListOf<SampleInfo>()
        roundEnv.getElementsAnnotatedWith(Sample::class.java).forEach { element ->
            logger.info("element=${element}, ${element.kind}; ")
            val annotation = element.getAnnotation(Sample::class.java)
            val group = annotation.group
            val item = annotation.item
            val createdAt = annotation.createdAt
            val updatedAt = annotation.updatedAt
            items.add(SampleInfo(group, item, createdAt, updatedAt))
        }

        //
        val packageName = SampleInfo.PACKAGE_CODEGEN
        val fileName = SampleInfo.FILE_NAME
        val file = FileSpec.builder(packageName, fileName)
            .addType(createSample(packageName, items))
            .addImport(SampleInfo.PACKAGE_CODEGEN, SampleInfo.CLASS_NAME)
            .build()
        try {
            file.writeTo(filer)
            logger.info(">>> $fileName has been generated. <<<")
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        logger.info(">>> SampleProcessor stop. <<<")
        return true
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
}
