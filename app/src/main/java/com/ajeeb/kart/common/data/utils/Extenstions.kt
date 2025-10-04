package com.ajeeb.kart.common.data.utils

import android.util.Log
import com.ajeeb.kart.common.domain.utils.Issues
import com.ajeeb.kart.common.domain.utils.Result
import com.google.gson.JsonSyntaxException
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_CONFLICT
import java.net.HttpURLConnection.HTTP_CREATED
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR
import java.net.HttpURLConnection.HTTP_NO_CONTENT
import java.net.HttpURLConnection.HTTP_OK
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import java.net.SocketTimeoutException
import java.net.UnknownHostException

inline fun <T> handledApiCall(
    apiCall: () -> Response<T>
): Result<T, Issues.Network> {
    try {
        val response = apiCall()
        val body = response.body()

        return when (response.code()) {
            HTTP_OK, HTTP_CREATED, HTTP_NO_CONTENT -> if (body != null) Result.Success(body) else Result.Error(
                Issues.Network.NoResponse
            )

            HTTP_INTERNAL_ERROR -> Result.Error(Issues.Network.InternalError)
            HTTP_UNAUTHORIZED -> Result.Error(Issues.Network.UnAuthorized)
            HTTP_BAD_REQUEST -> Result.Error(Issues.Network.BadRequest)
            HTTP_CONFLICT -> Result.Error(Issues.Network.ConflictFound())
            else -> Result.Error(Issues.Network.Unknown)
        }

    } catch (e: UnknownHostException) {
        return Result.Error(Issues.Network.NoInternet)
    } catch (e: SocketTimeoutException) {
        return Result.Error(Issues.Network.RequestTimeOut)
    } catch (e: JsonSyntaxException) {
        return Result.Error(Issues.Network.MappingFailed)
    } catch (e: Exception) {
        Log.i("mylog",e.toString())
        return Result.Error(Issues.Network.Unknown)
    }
}