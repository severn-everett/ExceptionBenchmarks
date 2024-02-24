package com.severett.exbenchmarks.model

@JvmInline
value class LilOutcome(val value: Int) {
    fun isSuccess() = value != FAILURE

    companion object {
        const val FAILURE = -1
    }
}
