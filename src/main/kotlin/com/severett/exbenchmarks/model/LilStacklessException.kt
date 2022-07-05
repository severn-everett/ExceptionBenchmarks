package com.severett.exbenchmarks.model

class LilStacklessException : LilException {
    constructor(metadata: Int) : super(metadata)

    constructor(e: LilStacklessException, metadata: Int) : super(e, metadata)

    // No-Op
    override fun fillInStackTrace() = this
}