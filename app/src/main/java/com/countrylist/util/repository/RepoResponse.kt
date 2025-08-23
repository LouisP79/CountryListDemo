package com.countrylist.util.repository

import retrofit2.Response

class RepoResponse<T>(val dataResponse: Response<T>?, val throwable: Throwable?) {
    companion object {
        fun <T> respond(dataResponse: Response<T>? = null, throwable: Throwable? = null): RepoResponse<T> {
            return RepoResponse(dataResponse, throwable)
        }
    }
}