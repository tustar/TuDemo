package com.tustar.ksp.processor

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.FileSpec
import com.tustar.annotation.Sample
import com.tustar.assist.SampleGenerator
import com.tustar.assist.SampleInfo
import com.tustar.assist.SampleInfo.Companion.toSampleInfo
import com.tustar.ksp.utils.Logger

class TuGenSymbolProcessor(private val environment: SymbolProcessorEnvironment) : SymbolProcessor {

    private val logger = Logger(environment)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(Sample::class.java.name)
        val (validSymbols, invalidSymbols) = symbols.partition { it.validate() }.toList()
        logger.info(">>> Found symbols, valid: $validSymbols, invalid: $invalidSymbols <<<")
        if (validSymbols.isNullOrEmpty()) {
            return invalidSymbols
        }

        val items = mutableListOf<SampleInfo>()
        validSymbols.forEach { annotated ->
            annotated.annotations.forEach { annotation ->
                if (annotation.annotationType.resolve().declaration.qualifiedName?.asString()
                    == Sample::class.qualifiedName
                ) {
                    items += annotation.toSampleInfo()
                }
            }
        }
        writeFile(SampleGenerator.buildFileSpec(items))
        return invalidSymbols
    }

    override fun finish() {
        super.finish()
        logger.info(">>> TuGenSymbolProcessor finish. <<<")
    }

    override fun onError() {
        super.onError()
        logger.info(">>> TuGenSymbolProcessor onError. <<<")
    }

    private fun writeFile(fileSpec: FileSpec) {
        environment.codeGenerator
            .createNewFile(
                Dependencies(true),
                fileSpec.packageName,
                fileSpec.name
            )
            .writer()
            .use {
                fileSpec.writeTo(it)
            }
    }
}