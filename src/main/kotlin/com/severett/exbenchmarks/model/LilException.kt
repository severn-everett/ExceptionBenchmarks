package com.severett.exbenchmarks.model

open class LilException : Exception {
    val metadata: Int

    constructor(metadata: Int) {
        this.metadata = metadata
    }

    constructor(e: LilException, metadata: Int) : super(e) {
        this.metadata = metadata
    }
}