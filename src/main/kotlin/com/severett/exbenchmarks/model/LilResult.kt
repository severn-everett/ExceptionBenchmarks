package com.severett.exbenchmarks.model

sealed class LilResult {
    class Success(val value: Int) : LilResult()
    class Failure(val metadata: Int) : LilResult()
}
