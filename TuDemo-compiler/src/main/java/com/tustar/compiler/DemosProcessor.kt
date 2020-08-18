package com.tustar.compiler

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.tustar.annotation.RowDemo
import com.tustar.annotation.RowGroup
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic

@AutoService(Processor::class)
class DemosProcessor : AbstractProcessor() {

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
        types.add(RowGroup::class.java.name)
        types.add(RowDemo::class.java.name)
        return types
    }


    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment):
            Boolean {
        val groups = mutableListOf<Group>()
        roundEnv.getElementsAnnotatedWith(RowGroup::class.java).forEach { element ->
            note("element=${element}, ${element.kind}; ")
            val annotation = element.getAnnotation(RowGroup::class.java)
            val id = annotation.id
            val name = annotation.name
            groups.add(Group(id, name))
        }
        //
        val demos = mutableListOf<Demo>()
        roundEnv.getElementsAnnotatedWith(RowDemo::class.java).forEach { element ->
            note("element=${element}, ${element.kind}; ")
            val annotation = element.getAnnotation(RowDemo::class.java)
            val groupId = annotation.groupId
            val name = annotation.name
            val actionId = annotation.actionId
            demos.add(Demo(groupId, name, actionId))
        }
        //
        val generateGroups = buildGroupFun(groups)
        val generateDemos = buildDemoFun(demos)
        val file = FileSpec.builder("com.tustar.demo.data",
                "GenerateData")
                .addFunction(generateGroups)
                .addFunction(generateDemos)
                .build()
        try {
            file.writeTo(filer)
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return true
    }

    private fun buildGroupFun(groups: MutableList<Group>): FunSpec {
        return buildFun(groups, "Group", "generateGroups") {
            "  " +
                    "it.add(%T(" +
                    " id =${it.id}," +
                    " name = ${it.name}))"
        }
    }

    private fun buildDemoFun(demos: MutableList<Demo>): FunSpec {
        return buildFun(demos, "Demo", "generateDemos") {
            "  " +
                    "it.add(%T(" +
                    " groupId =${it.groupId}," +
                    " name = ${it.name}," +
                    " actionId = ${it.actionId}))"
        }
    }

    private fun <T> buildFun(demos: MutableList<T>, className: String,
                             funName: String, block: (T) -> String): FunSpec {
        val tClazz = ClassName("com.tustar.demo.data", className)
        val arrayList = ClassName("kotlin.collections", "ArrayList")
        val arrayListOfT = arrayList.parameterizedBy(tClazz)
        val builder = FunSpec.builder(funName)
                .returns(arrayListOfT)
                .addStatement("val result = %T().also {", arrayListOfT)
        demos.forEach {
            builder.addStatement(block.invoke(it), tClazz)
        }
        builder.addStatement("}")
        builder.addStatement("return result")
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
