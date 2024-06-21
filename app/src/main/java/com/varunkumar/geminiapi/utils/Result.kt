package com.varunkumar.geminiapi.utils

sealed class Result<T>(val data: T? = null, val msg: String? = null) {
    class Loading<T> : Result<T>()
    class Success<T>(data: T?) : Result<T>(data = data)
    class Error<T>(msg: String?) : Result<T>(msg = msg)
}