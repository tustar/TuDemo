package com.tustar.compiler

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.tustar.annotation.DemoItem
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
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
        val generateDemos = buildDemoFun(demos)
        val file = FileSpec.builder(
            "com.tustar.demo.data.gen",
            "GenData"
        )
            .addFunction(generateDemos)
            .build()
        try {
            file.writeTo(filer)
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return true
    }

    private fun buildDemoFun(demos: MutableList<DemoInfo>): FunSpec {
        return buildFun(demos, "DemoItem", "generateDemos") {
            "    it.add(%T(group = ${it.group}, item = ${it.item}, " +
                    "createdAt = \"${it.createdAt}\", updatedAt = \"${it.updatedAt}\"))"
        }
    }

    private fun <T> buildFun(
        demos: MutableList<T>, className: String,
        funName: String, block: (T) -> String
    ): FunSpec {
        val tClazz = ClassName("com.tustar.demo.data.model", className)
        val arrayList = ClassName("kotlin.collections", "ArrayList")
        val arrayListOfT = arrayList.parameterizedBy(tClazz)
        val builder = FunSpec.builder(funName)
            .returns(arrayListOfT)
            .addStatement("val demos = %T().also {", arrayListOfT)
        demos.forEach {
            builder.addStatement(block.invoke(it), tClazz)
        }
        builder.addStatement("}")
        builder.addStatement("return demos")
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
