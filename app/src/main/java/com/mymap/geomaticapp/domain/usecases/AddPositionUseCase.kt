package com.mymap.geomaticapp.domain.usecases

import com.mymap.geomaticapp.data.api.request.AddPositionRequest
import com.mymap.geomaticapp.domain.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddPositionUseCase @Inject constructor(
    private val repository: Repository
) {

    operator fun invoke(request: AddPositionRequest): Flow<Boolean> =
        flow {
            val result = repository.addPosition(request = request)
            emit(result)
        }

}