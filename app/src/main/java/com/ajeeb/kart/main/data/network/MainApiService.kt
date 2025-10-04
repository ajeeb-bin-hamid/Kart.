package com.ajeeb.kart.main.data.network

import com.ajeeb.kart.main.data.model.GetProductsResponse
import retrofit2.Response
import retrofit2.http.GET

interface MainApiService {

    @GET("/")
    suspend fun getProducts(): Response<ArrayList<GetProductsResponse>>
}