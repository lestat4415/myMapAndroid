package com.mymap.geomaticapp.domain.usecases

import com.mymap.geomaticapp.domain.Repository
import com.mymap.geomaticapp.model.Positions
import javax.inject.Inject

class GetAllPointsUseCase @Inject constructor(
    private val repository: Repository
){

    suspend operator fun invoke(): List<Positions> = repository.getAllPositions()

}