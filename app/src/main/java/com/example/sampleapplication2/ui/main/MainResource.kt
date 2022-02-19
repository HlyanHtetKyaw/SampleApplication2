package com.example.sampleapplication2.ui.main

class MainResource<T>(val status: AuthStatus, val data: T?, val message: String?) {
    enum class AuthStatus {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T?): MainResource<T?> {
            return MainResource(AuthStatus.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): MainResource<T?> {
            return MainResource(AuthStatus.ERROR, data, msg)
        }

        fun <T> loading(data: T?): MainResource<T?> {
            return MainResource(AuthStatus.LOADING, data, null)
        }

    }
}