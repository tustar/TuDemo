package com.tustar.compiler

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.Import
import com.tustar.annotation.DemoItem
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import javax.tools.Diagnostic

@AutoService(Processor::class)
class DemoProcessor : AbstractProcessor() {

    private lateinit var typeUtils: Types
    private lateinit var elementUtils: Elements
    private lateinit var filer: Filer
    private lateinit var messager: Messager

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        typeUtils = processingEnv.typeUtils
        elementUtils = processingEnv.elementUtils
        filer = processingEnv.filer
        messager = processingEnv.messager
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val types: MutableSet<String> = LinkedHashSet()
        types.add(DemoItem::class.java.name)
        return types
    }


    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment):
            Boolean {
        val demos = mutableListOf<DemoInfo>()
        roundEnv.getElementsAnnotatedWith(DemoItem::class.java).forEach { element ->
            note("element=${element}, ${element.kind}; ")
            val annotation = element.getAnnotation(DemoItem::class.java)
            val group = annotation.group
            val item = annotation.item
            val createdAt = annotation.createdAt
            val updatedAt = annotation.updatedAt
            demos.add(DemoInfo(group, item, createdAt, updatedAt))
        }
        //
        val packageName = "com.tustar.demo.codegen"
        val fileName = "GenData"
        val generateDemos = buildDemoFun(packageName, demos)
        val file = FileSpec.builder(packageName, fileName)
            .addImport("com.tustar.demo.data", "DemoItem")
            .addFunction(generateDemos)
            .build()
        try {
            file.writeTo(filer)
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return true
    }

    private fun buildDemoFun(packageName: String, demos: MutableList<DemoInfo>): FunSpec {
        return buildFun(demos, packageName, className = "DemoItem", funName = "generateDemos") {
            "result += %T(group = ${it.group}, item = ${it.item}, " +
                    "createdAt = \"${it.createdAt}\", updatedAt = \"${it.updatedAt}\")"
        }
    }

    private fun <T> buildFun(
        demos: MutableList<T>,
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
        demos.forEach {
            builder.addStatement(block.invoke(it), tClazz)
        }
        builder.addStatement("return result.sortedByDescending(DemoItem::updatedAt)")
        return builder.build()
    }

    private fun note(msg: String) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg + "\n")
    }

    private fun note(format: String, vararg args: Any) {
        messager.printMessage(Diagnostic.Kind.NOTE, String.format(format, *args) + "\n")
    }

    private fun error(msg: String) {
        messager.printMessage(Diagnostic.Kind.ERROR, msg + "\n")
    }

    private fun error(format: String, vararg args: Any) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(format, *args) + "\n")
    }

    companion object {
        private const val TAG = "DemosProcessor"
    }
}
