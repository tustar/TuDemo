package com.tustar.compiler.utils

import javax.annotation.processing.Messager
import javax.tools.Diagnostic

class Logger(private val messager: Messager) {

    /**
     * Print info log.
     */
    fun info(info: CharSequence) {
        if (info.isEmpty()) {
            return
        }
        messager.printMessage(Diagnostic.Kind.NOTE, Consts.PREFIX_OF_LOGGER + info)
    }

    fun error(error: CharSequence) {
        if (error.isEmpty()) {
            return
        }
        messager.printMessage(
            Diagnostic.Kind.ERROR,
            "${Consts.PREFIX_OF_LOGGER}An exception is encountered, [$error]"
        )
    }

    fun warning(warning: CharSequence) {
        if (warning.isEmpty()) {
            return
        }
        messager.printMessage(Diagnostic.Kind.WARNING, Consts.PREFIX_OF_LOGGER + warning)
    }

    fun error(error: Throwable?) {
        if (error == null) {
            return
        }

        messager.printMessage(
            Diagnostic.Kind.ERROR,
            "${Consts.PREFIX_OF_LOGGER}An exception is encountered, [${error.message}]\n" + formatStackTrace(
                error.stackTrace
            )
        )
    }

    private fun formatStackTrace(stackTrace: Array<StackTraceElement>): String {
        val sb = StringBuilder()
        for (element in stackTrace) {
            sb.append("    at ").append(element.toString())
            sb.append("\n")
        }
        return sb.toString()
    }
}