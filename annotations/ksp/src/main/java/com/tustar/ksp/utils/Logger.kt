package com.tustar.ksp.utils

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import javax.annotation.processing.Messager
import javax.tools.Diagnostic

class Logger(private val environment: SymbolProcessorEnvironment) {

    private val logger = environment.logger

    fun logging(logging: String) {
        logger.logging(Consts.PREFIX_OF_LOGGER + logging)
    }

    fun info(info: String) {
        logger.info(Consts.PREFIX_OF_LOGGER + info)
    }

    fun error(error: String) {
        logger.error(Consts.PREFIX_OF_LOGGER + error)
    }

    fun warning(warning: String) {
        logger.warn(Consts.PREFIX_OF_LOGGER + warning)
    }

    fun error(error: Throwable?) {
        if (error == null) {
            return
        }

        logger.error(
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