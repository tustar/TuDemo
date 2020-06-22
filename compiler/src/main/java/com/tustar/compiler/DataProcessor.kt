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


@AutoService(Process::class)
@SuppressWarnings("NullAway")
public class DataProcessor : AbstractProcessor() {

    private val OPTION_SDK_INT = "tu.minSdk"
    private val OPTION_DEBUGGABLE = "tu.debuggable"

    private lateinit var typeUtils: Types
    private lateinit var elementUtils: Elements
    private lateinit var filer: Filer
    private lateinit var messager: Messager

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)

        typeUtils = processingEnv.typeUtils;
        elementUtils = processingEnv.elementUtils;
        filer = processingEnv.filer;
        messager = processingEnv.messager;
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported();
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val types: MutableSet<String> = LinkedHashSet()
        for (annotation in getSupportedAnnotations()) {
            types.add(annotation.canonicalName)
        }
        return types
    }

    private fun getSupportedAnnotations(): Set<Class<out Annotation>> {
        val annotations: MutableSet<Class<out Annotation>> = LinkedHashSet()
        annotations.add(RowGroup::class.java)
        annotations.add(RowDemo::class.java)
        return annotations
    }

    override fun getSupportedOptions(): MutableSet<String> {
        return super.getSupportedOptions()
    }

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment):
            Boolean {
        println("DataProcessor::process")
        val groups = mutableListOf<Group>()
        roundEnv.getElementsAnnotatedWith(RowGroup::class.java).forEach { element ->
            if (element.kind != ElementKind.FIELD) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Only field can can be annotated " +
                        "with ${RowGroup::class.java.simpleName}", element)
                return true
            }

            val annotation = element.getAnnotation(RowGroup::class.java)
            val id = annotation.id
            val name = annotation.name
            groups.add(Group(id, name))
        }
        println(JSON.toJSONString(groups))

        roundEnv.getElementsAnnotatedWith(RowDemo::class.java).forEach { element ->
            if (element.kind != ElementKind.CLASS) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Only field can can be annotated " +
                        "with ${RowDemo::class.java.simpleName}", element)
                return true
            }
        }

        return true
    }
}
