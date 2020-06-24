package com.tustar.compiler

import com.alibaba.fastjson.JSON
import com.google.auto.service.AutoService
import com.tustar.annotation.RowDemo
import com.tustar.annotation.RowGroup
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
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
        return SourceVersion.latestSupported()
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
            println("element=${element}")
            if (element.kind != ElementKind.CLASS) {
                error("Only field can can be annotated " +
                        "with ${RowGroup::class.java.simpleName}", element)
                return true
            }

            val annotation = element.getAnnotation(RowGroup::class.java)
            val id = annotation.id
            val name = annotation.name
            groups.add(Group(id, name))
        }
        println("Json=${JSON.toJSONString(groups)}")

//        val demos = mutableListOf<Demo>()
//        roundEnv.getElementsAnnotatedWith(RowDemo::class.java).forEach { element ->
//            if (element.kind != ElementKind.CLASS) {
//                error("Only field can can be annotated " +
//                        "with ${RowDemo::class.java.simpleName}", element)
//                return true
//            }
//
//            val annotation = element.getAnnotation(RowDemo::class.java)
//            val groupId = annotation.groupId
//            val name = annotation.name
//            val actionId = annotation.actionId
//            demos.add(Demo(groupId, name, actionId))
//        }
//        note(JSON.toJSONString(groups))

//        val greeterClass = ClassName("com.tustar.demo.data", "Greeter")
//        val file = FileSpec.builder("com.tustar.demo.data", "HelloWorld")
//                .addType(
//                        TypeSpec.classBuilder("Greeter")
//                                .primaryConstructor(
//                                        FunSpec.constructorBuilder()
//                                                .addParameter("name", String::class)
//                                                .build()
//                                )
//                                .addProperty(
//                                        PropertySpec.builder("name", String::class)
//                                                .initializer("name")
//                                                .build()
//                                )
//                                .addFunction(
//                                        FunSpec.builder("greet")
//                                                .addStatement("println(%P)", "Hello, \$name")
//                                                .build()
//                                )
//                                .build()
//                )
//                .addFunction(
//                        FunSpec.builder("main")
//                                .addParameter("args", String::class, KModifier.VARARG)
//                                .addStatement("%T(args[0]).greet()", greeterClass)
//                                .build()
//                )
//                .build()
//
//        try {
//            file.writeTo(System.out)
//        } catch (e: Throwable) {
//            e.printStackTrace()
//        }

        return false
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
