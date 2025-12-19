package com.severett.exbenchmarks.visualizer

import org.jetbrains.kotlinx.kandy.dsl.continuous
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.errorBars
import org.jetbrains.kotlinx.kandy.letsplot.layers.line
import org.jetbrains.kotlinx.kandy.letsplot.scales.Transformation
import org.jetbrains.kotlinx.kandy.letsplot.settings.font.FontFace
import org.jetbrains.kotlinx.kandy.letsplot.x
import org.jetbrains.kotlinx.kandy.util.context.invoke
import java.io.File

private const val PPM_KEY = "PPM"
private const val EXECUTION_TIME_KEY = "Execution Time (ns)"

fun main(args: Array<String>) {
    val runData = parseFile(args[0])
    plot {
        x((0 until runData.size).flatMap { runData.ppm }) {
            axis.name = PPM_KEY
            scale = continuous(transform = Transformation.LOG10)
        }
        line {
            y(runData.exceptionRuns.flatMap { it.results }) {
                axis.name = EXECUTION_TIME_KEY
                scale = continuous(transform = Transformation.LOG10)
            }
            errorBars {
                yMin(runData.exceptionRuns.flatMap { it.errorLow })
                yMax(runData.exceptionRuns.flatMap { it.errorHigh })
                borderLine.color(runData.exceptionRuns.flatMap { it.colorValues })
            }
            color(runData.exceptionRuns.flatMap { it.colorValues }) {
                legend.name = ""
            }
        }
        layout {
            size = 900 to 500
            style {
                axis.title { fontFace = FontFace.BOLD }
                legend {
                    justification(1.0, 1.0)
                    position(0.25, 1.0)
                }
            }
        }
    }.save(
        filename = args[1],
        path = "results/graphs"
    )
}

private fun parseFile(filename: String): RunData {
    val ppm = mutableListOf<Int>()
    var lineNumber = 1
    val exceptionRuns = mutableListOf<ExceptionRun>()
    File(filename).forEachLine { line ->
        val entries = line.split(",")
        if (lineNumber == 1) {
            exceptionRuns.addAll(parseHeaders(entries))
        } else {
            parseIteration(entries = entries, ppm = ppm, exceptionRuns = exceptionRuns)
        }
        lineNumber++
    }

    return RunData(ppm = ppm, exceptionRuns = exceptionRuns)
}

private fun parseHeaders(entries: List<String>): List<ExceptionRun> {
    return (1 until entries.size step 2).map { i ->
        ExceptionRun(entries[i])
    }
}

private fun parseIteration(entries: List<String>, ppm: MutableList<Int>, exceptionRuns: List<ExceptionRun>) {
    var erIndex = 0
    ppm.add(entries[0].toInt())
    (1 until entries.size step 2).forEach { i ->
        val result = entries[i].toDouble()
        val error = entries[i + 1].toDouble()
        exceptionRuns[erIndex++].addResult(result, error)
    }
}

private class ExceptionRun(val title: String) {
    fun addResult(result: Double, error: Double) {
        _results.add(result)
        _errorHigh.add(result + error)
        _errorLow.add(result - error)
    }

    private val _results = mutableListOf<Double>()
    private val _errorHigh = mutableListOf<Double>()
    private val _errorLow = mutableListOf<Double>()

    val results: List<Double>
        get() = _results
    val colorValues: List<String>
        get() = _results.map { title }
    val errorHigh: List<Double>
        get() = _errorHigh
    val errorLow: List<Double>
        get() = _errorLow
}

private class RunData(val ppm: List<Int>, val exceptionRuns: List<ExceptionRun>) {
    val size = exceptionRuns.size
}
