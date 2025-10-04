package com.ajeeb.kart.main.data.repository

import com.ajeeb.kart.common.data.utils.handledApiCall
import com.ajeeb.kart.common.domain.utils.Issues
import com.ajeeb.kart.common.domain.utils.Result
import com.ajeeb.kart.main.data.network.MainApiService
import com.ajeeb.kart.main.data.utils.mapToProducts
import com.ajeeb.kart.main.domain.model.Product
import com.ajeeb.kart.main.domain.repository.MainApiRepository


class MainApiRepositoryImpl(
    private val mainApiService: MainApiService
) : MainApiRepository {

    override suspend fun getProducts(): Result<ArrayList<Product>, Issues.Network> {

        val apiCall = handledApiCall {
            mainApiService.getProducts()
        }

        return when (apiCall) {
            is Result.Success -> {
                val products = mapToProducts(apiCall.data)
                Result.Success(products)
            }

            is Result.Error -> Result.Error(apiCall.error)
        }
    }
}