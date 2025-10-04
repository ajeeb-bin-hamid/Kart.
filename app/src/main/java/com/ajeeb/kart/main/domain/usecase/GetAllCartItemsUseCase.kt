package com.ajeeb.kart.main.domain.usecase

import com.ajeeb.kart.main.domain.repository.MainKartDbRepository
import javax.inject.Inject

class GetAllCartItemsUseCase @Inject constructor(
    private val mainKartDbRepository: MainKartDbRepository
) {
    operator fun invoke() = mainKartDbRepository.getAllCartItems()
}