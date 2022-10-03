package com.tustar.compiler.processor

import com.google.auto.service.AutoService
import com.tustar.annotation.Sample
import com.tustar.assist.SampleGenerator
import com.tustar.assist.SampleInfo
import com.tustar.assist.SampleInfo.Companion.toSampleInfo
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
class TuGenAnnotationProcessor : BaseProcessor() {

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        logger.info(">>> TuGenAnnotationProcessor init. <<<")
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val types: MutableSet<String> = LinkedHashSet()
        types.add(Sample::class.java.name)
        return types
    }

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment):
            Boolean {
        if (annotations.isEmpty()) {
            return false
        }
        logger.info(">>> TuGenAnnotationProcessor process. <<<")
        val items = mutableListOf<SampleInfo>()
        roundEnv.getElementsAnnotatedWith(Sample::class.java).forEach { element ->
            logger.info(">>> ${element}, ${element.kind} <<<")
            items += element.toSampleInfo()
        }
        val fileSpec = SampleGenerator.buildFileSpec(items)
        try {
            fileSpec.writeTo(filer)
            logger.info(">>> ${fileSpec.name} has been generated. <<<")
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        logger.info(">>> TuGenAnnotationProcessor stop. <<<")
        return true
    }
}
