package com.severett.exbenchmarks.model

inline class LilOutcome(val value: Int) {
    fun isSuccess() = value != FAILURE

    companion object {
        const val FAILURE = -1
        fun createFailure() = LilOutcome(FAILURE)
    }
}
