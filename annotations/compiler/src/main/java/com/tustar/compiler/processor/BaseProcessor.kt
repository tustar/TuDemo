package com.tustar.compiler.processor

import com.tustar.compiler.utils.Logger
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.util.Elements
import javax.lang.model.util.Types


abstract class BaseProcessor : AbstractProcessor() {

    private lateinit var typeUtils: Types
    private lateinit var elementUtils: Elements
    lateinit var filer: Filer
    lateinit var messager: Messager
    lateinit var logger: Logger


    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        typeUtils = processingEnv.typeUtils
        elementUtils = processingEnv.elementUtils
        filer = processingEnv.filer
        messager = processingEnv.messager
        //
        logger = Logger(processingEnv.messager)

        // Attempt to get user configuration [moduleName]
//        val options = processingEnv.options
//        if (options.isNotEmpty()) {
//            options.forEach { (k, v) -> logger.info("options: $k->$v") }
//        }
    }

    override fun getSupportedSourceVersion(): SourceVersion {
         return SourceVersion.latestSupported()
    }
}