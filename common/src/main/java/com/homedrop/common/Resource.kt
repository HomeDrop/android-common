package com.homedrop.common

class Resource<T> private constructor(
    val status: Status,
    private val _data: T? = null,
    private val _message: String? = null
) {

    val data = _data!!

    val message = _message.toString()

    companion object {

        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }

        fun <T> error(msg: String?): Resource<T> {
            return Resource(Status.ERROR, _message = msg)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING)
        }

    }

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

}