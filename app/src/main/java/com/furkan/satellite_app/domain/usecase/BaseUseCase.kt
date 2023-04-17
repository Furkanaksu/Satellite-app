package com.furkan.satellite_app.domain.usecase

import com.furkan.satellite_app.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseUseCase<in Request, Type> {

    abstract suspend fun execute(request: Request): Resource<Type>

    suspend operator fun invoke(request: Request): Resource<Type> {
        return withContext(Dispatchers.IO) {
            execute(request)
        }
    }
}