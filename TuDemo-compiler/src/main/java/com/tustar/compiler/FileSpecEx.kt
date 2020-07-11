package com.tustar.compiler

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.OriginatingElementsHolder
import java.io.File
import java.io.IOException
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import javax.annotation.processing.Filer
import javax.tools.StandardLocation

/** Writes this to `filer`.  */
@Throws(IOException::class)
fun FileSpec.writeToApp(filer: Filer) {
    val originatingElements = members.asSequence()
            .filterIsInstance<OriginatingElementsHolder>()
            .flatMap { it.originatingElements.asSequence() }
            .toSet()
    val filerSourceFile = filer.createResource(StandardLocation.SOURCE_OUTPUT,
            "",
            "$name.kt",
            *originatingElements.toTypedArray()
    )
    val dest = filerSourceFile.toUri().path
            .replaceFirst("hencoder", "app")
            .replace("$name.kt", "")
    try {
        writeTo(File(dest))
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            filerSourceFile.delete()
        } catch (ignored: Exception) {
        }
    }
}