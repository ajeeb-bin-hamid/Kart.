package com.ajeeb.kart.main.domain.usecase

import com.ajeeb.kart.common.domain.utils.Issues
import com.ajeeb.kart.common.domain.utils.Result
import com.ajeeb.kart.main.domain.model.Product
import com.ajeeb.kart.main.domain.repository.MainKartDbRepository
import javax.inject.Inject

class RemoveItemToCartUseCase @Inject constructor(
    private val mainKartDbRepository: MainKartDbRepository
) {
    suspend operator fun invoke(product: Product): Result<Unit, Issues.Database> {
        return mainKartDbRepository.removeItemToCart(product)
    }
}