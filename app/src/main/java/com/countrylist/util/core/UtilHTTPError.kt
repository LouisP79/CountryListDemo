package com.countrylist.util.core

import com.countrylist.R
import java.net.HttpURLConnection

fun errorCode(code: Int): Int{
    return when(code){
        HttpURLConnection.HTTP_BAD_REQUEST -> R.string.user_error
        HttpURLConnection.HTTP_UNAUTHORIZED -> R.string.unauthorized_error
        HttpURLConnection.HTTP_BAD_METHOD -> R.string.bad_method_error
        HttpURLConnection.HTTP_NOT_FOUND -> R.string.not_found_error
        else -> R.string.unknown_error
    }
}