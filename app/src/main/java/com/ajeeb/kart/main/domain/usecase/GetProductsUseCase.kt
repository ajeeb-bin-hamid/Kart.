package com.ajeeb.kart.main.domain.usecase

import com.ajeeb.kart.common.domain.utils.Issues
import com.ajeeb.kart.common.domain.utils.Result
import com.ajeeb.kart.main.domain.model.Product
import com.ajeeb.kart.main.domain.repository.MainApiRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val mainApiRepository: MainApiRepository
) {
    suspend operator fun invoke(): Result<ArrayList<Product>, Issues.Network> {
        return mainApiRepository.getProducts()
    }
}