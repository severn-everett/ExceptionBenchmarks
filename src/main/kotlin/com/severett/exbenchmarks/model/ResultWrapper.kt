package com.severett.exbenchmarks.model

class ResultWrapper(private var _result: Int) {
    val result: Int
        get() = _result

    operator fun times(other: Int): ResultWrapper {
        _result *= other
        return this
    }
}
