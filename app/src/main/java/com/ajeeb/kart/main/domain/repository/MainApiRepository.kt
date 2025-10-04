package com.ajeeb.kart.main.domain.repository

import com.ajeeb.kart.common.domain.utils.Issues
import com.ajeeb.kart.common.domain.utils.Result
import com.ajeeb.kart.main.domain.model.Product

interface MainApiRepository {
    suspend fun getProducts(): Result<ArrayList<Product>, Issues.Network>
}