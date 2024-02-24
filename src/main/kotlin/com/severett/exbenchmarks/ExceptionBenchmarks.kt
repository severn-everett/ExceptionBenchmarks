package com.severett.exbenchmarks

import com.severett.exbenchmarks.model.LilException
import com.severett.exbenchmarks.model.LilOutcome
import com.severett.exbenchmarks.model.LilOutcome.Companion.FAILURE
import com.severett.exbenchmarks.model.LilResult
import com.severett.exbenchmarks.model.LilStacklessException
import com.severett.exbenchmarks.model.ResultWrapper
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder
import org.openjdk.jmh.runner.options.TimeValue
import org.openjdk.jmh.runner.options.VerboseMode
import java.util.concurrent.TimeUnit
import kotlin.random.Random

private const val DELINEATOR = ","
private val PARTS = Integer.getInteger("exceptPPM", 0)
private const val CEILING = 1_000_000
private const val RESULT_PRIMITIVE_RAW = "Result Primitive"
private const val RESULT_PRIMITIVE_OUT = "Result (Primitive)"
private const val RESULT_WRAPPER_RAW = "Result With Wrapper"
private const val RESULT_WRAPPER_OUT = "Result (w/ Wrapper)"

@Suppress("RedundantNullableReturnType")
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(5)
@State(Scope.Thread)
open class ExceptionBenchmarks {

    private val source = 42
    private lateinit var staticException: LilException
    private lateinit var staticStacklessException: LilStacklessException

    @Setup(Level.Iteration)
    fun setup() {
        staticException = LilException(source)
        staticStacklessException = LilStacklessException(source)
    }

    @Benchmark
    fun dynamic_exception() = try {
        de01()
    } catch (e: LilException) {
        e.metadata
    }

    @Benchmark
    fun dynamic_stackless() = try {
        ds01()
    } catch (e: LilException) {
        e.metadata
    }

    @Benchmark
    fun static_exception() = try {
        se01()
    } catch (e: LilException) {
        e.metadata
    }

    @Benchmark
    fun flags() = m01()

    @Benchmark
    fun sealed_class() = when (val result = sc01()) {
        is LilResult.Success -> result.value
        is LilResult.Failure -> result.metadata
    }

    @Benchmark
    fun result_primitive() = rp01().fold({ it }, { (it as LilException).metadata })

    @Benchmark
    fun result_with_wrapper() = rrw01().fold({ it }, { (it as LilException).metadata })

    @Benchmark
    fun inline_class() = ic01().value

    @Benchmark
    fun inline_autobox() = ia01()?.value ?: FAILURE

    private fun de01() = de02() * 2
    private fun de02() = de03() * 2
    private fun de03() = de04() * 2
    private fun de04() = de05() * 2
    private fun de05() = de06() * 2
    private fun de06() = de07() * 2
    private fun de07() = de08() * 2
    private fun de08() = de09() * 2
    private fun de09() = de10() * 2
    private fun de10() = de11() * 2
    private fun de11() = de12() * 2
    private fun de12() = de13() * 2
    private fun de13() = de14() * 2
    private fun de14() = de15() * 2
    private fun de15() = de16() * 2
    private fun de16(): Int {
        return if (callSucceeded()) source else throw LilException(source)
    }

    private fun ds01() = ds02() * 2
    private fun ds02() = ds03() * 2
    private fun ds03() = ds04() * 2
    private fun ds04() = ds05() * 2
    private fun ds05() = ds06() * 2
    private fun ds06() = ds07() * 2
    private fun ds07() = ds08() * 2
    private fun ds08() = ds09() * 2
    private fun ds09() = ds10() * 2
    private fun ds10() = ds11() * 2
    private fun ds11() = ds12() * 2
    private fun ds12() = ds13() * 2
    private fun ds13() = ds14() * 2
    private fun ds14() = ds15() * 2
    private fun ds15() = ds16() * 2
    private fun ds16(): Int {
        return if (callSucceeded()) source else throw LilStacklessException(source)
    }

    private fun se01() = se02() * 2
    private fun se02() = se03() * 2
    private fun se03() = se04() * 2
    private fun se04() = se05() * 2
    private fun se05() = se06() * 2
    private fun se06() = se07() * 2
    private fun se07() = se08() * 2
    private fun se08() = se09() * 2
    private fun se09() = se10() * 2
    private fun se10() = se11() * 2
    private fun se11() = se12() * 2
    private fun se12() = se13() * 2
    private fun se13() = se14() * 2
    private fun se14() = se15() * 2
    private fun se15() = se16() * 2
    private fun se16(): Int {
        return if (callSucceeded()) source else throw staticException
    }

    private fun m01() = m02().let { if (it != -1) it * 2 else it }
    private fun m02() = m03().let { if (it != -1) it * 2 else it }
    private fun m03() = m04().let { if (it != -1) it * 2 else it }
    private fun m04() = m05().let { if (it != -1) it * 2 else it }
    private fun m05() = m06().let { if (it != -1) it * 2 else it }
    private fun m06() = m07().let { if (it != -1) it * 2 else it }
    private fun m07() = m08().let { if (it != -1) it * 2 else it }
    private fun m08() = m09().let { if (it != -1) it * 2 else it }
    private fun m09() = m10().let { if (it != -1) it * 2 else it }
    private fun m10() = m11().let { if (it != -1) it * 2 else it }
    private fun m11() = m12().let { if (it != -1) it * 2 else it }
    private fun m12() = m13().let { if (it != -1) it * 2 else it }
    private fun m13() = m14().let { if (it != -1) it * 2 else it }
    private fun m14() = m15().let { if (it != -1) it * 2 else it }
    private fun m15() = m16().let { if (it != -1) it * 2 else it }
    private fun m16(): Int {
        return if (callSucceeded()) source else -1
    }

    private fun sc01() = when (val result = sc02()) {
        is LilResult.Success -> LilResult.Success(result.value)
        else -> result
    }
    private fun sc02() = when (val result = sc03()) {
        is LilResult.Success -> LilResult.Success(result.value)
        else -> result
    }
    private fun sc03() = when (val result = sc04()) {
        is LilResult.Success -> LilResult.Success(result.value)
        else -> result
    }
    private fun sc04() = when (val result = sc05()) {
        is LilResult.Success -> LilResult.Success(result.value)
        else -> result
    }
    private fun sc05() = when (val result = sc06()) {
        is LilResult.Success -> LilResult.Success(result.value)
        else -> result
    }
    private fun sc06() = when (val result = sc07()) {
        is LilResult.Success -> LilResult.Success(result.value)
        else -> result
    }
    private fun sc07() = when (val result = sc08()) {
        is LilResult.Success -> LilResult.Success(result.value)
        else -> result
    }
    private fun sc08() = when (val result = sc09()) {
        is LilResult.Success -> LilResult.Success(result.value)
        else -> result
    }
    private fun sc09() = when (val result = sc10()) {
        is LilResult.Success -> LilResult.Success(result.value)
        else -> result
    }
    private fun sc10() = when (val result = sc11()) {
        is LilResult.Success -> LilResult.Success(result.value)
        else -> result
    }
    private fun sc11() = when (val result = sc12()) {
        is LilResult.Success -> LilResult.Success(result.value)
        else -> result
    }
    private fun sc12() = when (val result = sc13()) {
        is LilResult.Success -> LilResult.Success(result.value)
        else -> result
    }
    private fun sc13() = when (val result = sc14()) {
        is LilResult.Success -> LilResult.Success(result.value)
        else -> result
    }
    private fun sc14() = when (val result = sc15()) {
        is LilResult.Success -> LilResult.Success(result.value)
        else -> result
    }
    private fun sc15() = when (val result = sc16()) {
        is LilResult.Success -> LilResult.Success(result.value)
        else -> result
    }
    private fun sc16(): LilResult {
        return if (callSucceeded()) LilResult.Success(source) else LilResult.Failure(source)
    }

    private fun rp01() = rp02().map { it * 2 }
    private fun rp02() = rp03().map { it * 2 }
    private fun rp03() = rp04().map { it * 2 }
    private fun rp04() = rp05().map { it * 2 }
    private fun rp05() = rp06().map { it * 2 }
    private fun rp06() = rp07().map { it * 2 }
    private fun rp07() = rp08().map { it * 2 }
    private fun rp08() = rp09().map { it * 2 }
    private fun rp09() = rp10().map { it * 2 }
    private fun rp10() = rp11().map { it * 2 }
    private fun rp11() = rp12().map { it * 2 }
    private fun rp12() = rp13().map { it * 2 }
    private fun rp13() = rp14().map { it * 2 }
    private fun rp14() = rp15().map { it * 2 }
    private fun rp15() = rp16().map { it * 2 }
    private fun rp16(): Result<Int> {
        return if (callSucceeded()) Result.success(source) else Result.failure(LilException(source))
    }

    private fun rrw01() = rrw02().map { it * 2 }
    private fun rrw02() = rrw03().map { it * 2 }
    private fun rrw03() = rrw04().map { it * 2 }
    private fun rrw04() = rrw05().map { it * 2 }
    private fun rrw05() = rrw06().map { it * 2 }
    private fun rrw06() = rrw07().map { it * 2 }
    private fun rrw07() = rrw08().map { it * 2 }
    private fun rrw08() = rrw09().map { it * 2 }
    private fun rrw09() = rrw10().map { it * 2 }
    private fun rrw10() = rrw11().map { it * 2 }
    private fun rrw11() = rrw12().map { it * 2 }
    private fun rrw12() = rrw13().map { it * 2 }
    private fun rrw13() = rrw14().map { it * 2 }
    private fun rrw14() = rrw15().map { it * 2 }
    private fun rrw15() = rrw16().map { it.times(2) }
    private fun rrw16(): Result<ResultWrapper> {
        return if (callSucceeded()) Result.success(ResultWrapper(source)) else Result.failure(LilException(source))
    }

    private fun ic01() = ic02().let { if (it.isSuccess()) LilOutcome(it.value * 2) else it }
    private fun ic02() = ic03().let { if (it.isSuccess()) LilOutcome(it.value * 2) else it }
    private fun ic03() = ic04().let { if (it.isSuccess()) LilOutcome(it.value * 2) else it }
    private fun ic04() = ic05().let { if (it.isSuccess()) LilOutcome(it.value * 2) else it }
    private fun ic05() = ic06().let { if (it.isSuccess()) LilOutcome(it.value * 2) else it }
    private fun ic06() = ic07().let { if (it.isSuccess()) LilOutcome(it.value * 2) else it }
    private fun ic07() = ic08().let { if (it.isSuccess()) LilOutcome(it.value * 2) else it }
    private fun ic08() = ic09().let { if (it.isSuccess()) LilOutcome(it.value * 2) else it }
    private fun ic09() = ic10().let { if (it.isSuccess()) LilOutcome(it.value * 2) else it }
    private fun ic10() = ic11().let { if (it.isSuccess()) LilOutcome(it.value * 2) else it }
    private fun ic11() = ic12().let { if (it.isSuccess()) LilOutcome(it.value * 2) else it }
    private fun ic12() = ic13().let { if (it.isSuccess()) LilOutcome(it.value * 2) else it }
    private fun ic13() = ic14().let { if (it.isSuccess()) LilOutcome(it.value * 2) else it }
    private fun ic14() = ic15().let { if (it.isSuccess()) LilOutcome(it.value * 2) else it }
    private fun ic15() = ic16().let { if (it.isSuccess()) LilOutcome(it.value * 2) else it }
    private fun ic16(): LilOutcome {
        return if (callSucceeded()) LilOutcome(source) else LilOutcome(FAILURE)
    }

    private fun ia01(): LilOutcome? {
        val outcome = ia02()
        return if (outcome?.isSuccess() == true) LilOutcome(outcome.value) else LilOutcome(FAILURE)
    }
    private fun ia02(): LilOutcome? {
        val outcome = ia03()
        return if (outcome?.isSuccess() == true) LilOutcome(outcome.value) else LilOutcome(FAILURE)
    }
    private fun ia03(): LilOutcome? {
        val outcome = ia04()
        return if (outcome?.isSuccess() == true) LilOutcome(outcome.value) else LilOutcome(FAILURE)
    }
    private fun ia04(): LilOutcome? {
        val outcome = ia05()
        return if (outcome?.isSuccess() == true) LilOutcome(outcome.value) else LilOutcome(FAILURE)
    }
    private fun ia05(): LilOutcome? {
        val outcome = ia06()
        return if (outcome?.isSuccess() == true) LilOutcome(outcome.value) else LilOutcome(FAILURE)
    }
    private fun ia06(): LilOutcome? {
        val outcome = ia07()
        return if (outcome?.isSuccess() == true) LilOutcome(outcome.value) else LilOutcome(FAILURE)
    }
    private fun ia07(): LilOutcome? {
        val outcome = ia08()
        return if (outcome?.isSuccess() == true) LilOutcome(outcome.value) else LilOutcome(FAILURE)
    }
    private fun ia08(): LilOutcome? {
        val outcome = ia09()
        return if (outcome?.isSuccess() == true) LilOutcome(outcome.value) else LilOutcome(FAILURE)
    }
    private fun ia09(): LilOutcome? {
        val outcome = ia10()
        return if (outcome?.isSuccess() == true) LilOutcome(outcome.value) else LilOutcome(FAILURE)
    }
    private fun ia10(): LilOutcome? {
        val outcome = ia11()
        return if (outcome?.isSuccess() == true) LilOutcome(outcome.value) else LilOutcome(FAILURE)
    }
    private fun ia11(): LilOutcome? {
        val outcome = ia12()
        return if (outcome?.isSuccess() == true) LilOutcome(outcome.value) else LilOutcome(FAILURE)
    }
    private fun ia12(): LilOutcome? {
        val outcome = ia13()
        return if (outcome?.isSuccess() == true) LilOutcome(outcome.value) else LilOutcome(FAILURE)
    }
    private fun ia13(): LilOutcome? {
        val outcome = ia14()
        return if (outcome?.isSuccess() == true) LilOutcome(outcome.value) else LilOutcome(FAILURE)
    }
    private fun ia14(): LilOutcome? {
        val outcome = ia15()
        return if (outcome?.isSuccess() == true) LilOutcome(outcome.value) else LilOutcome(FAILURE)
    }
    private fun ia15(): LilOutcome? {
        val outcome = ia16()
        return if (outcome?.isSuccess() == true) LilOutcome(outcome.value) else LilOutcome(FAILURE)
    }
    private fun ia16(): LilOutcome? {
        return if (callSucceeded()) LilOutcome(source) else LilOutcome(FAILURE)
    }

    private fun callSucceeded(): Boolean {
        return Random.nextInt(CEILING) >= PARTS
    }
}

fun main() {
    var headerPrinted = false

    var base = 1
    while (base < CEILING) {
        for (mult in 1 until 10) {
            val ppm = base * mult
            val opt = OptionsBuilder()
                .include(".*${ExceptionBenchmarks::class.java.simpleName}.*")
                .measurementIterations(5)
                .measurementTime(TimeValue.seconds(1))
                .warmupIterations(5)
                .warmupTime(TimeValue.seconds(1))
                .forks(1)
                .jvmArgs("-DexceptPPM=$ppm")
                .verbosity(VerboseMode.SILENT)
                .build()

            val results = Runner(opt).run()

            if (!headerPrinted) {
                print("PPM$DELINEATOR")
                val headerLine = results.joinToString(DELINEATOR) { result ->
                    var label = result.primaryResult.label.split("_")
                        .joinToString(" ") { word ->
                            word.replaceFirstChar(Char::uppercaseChar)
                        }
                    if (label == RESULT_PRIMITIVE_RAW) {
                        label = RESULT_PRIMITIVE_OUT
                    } else if (label == RESULT_WRAPPER_RAW) {
                        label = RESULT_WRAPPER_OUT
                    }
                    "$label$DELINEATOR$label Error"
                }
                println(headerLine)
                headerPrinted = true
            }

            print("$ppm,")
            val resultLine = results.joinToString(DELINEATOR) { result ->
                val score = "%.3f".format(result.primaryResult.score)
                val error = "%.3f".format(result.primaryResult.statistics.getMeanErrorAt(0.99))
                "$score$DELINEATOR$error"
            }
            println(resultLine)
        }
        base *= 10
    }
}
