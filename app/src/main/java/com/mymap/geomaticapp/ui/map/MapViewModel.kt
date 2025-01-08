package com.mymap.geomaticapp.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.mymap.geomaticapp.domain.usecases.GetAllPointsUseCase
import com.mymap.geomaticapp.model.Positions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getPositions: GetAllPointsUseCase
) : ViewModel() {

    private val logTAG = MapViewModel::class.java.simpleName

    val defaultLocation = LatLng(19.42847, -99.12766)

    private val _ubicacionActual = MutableStateFlow<LatLng?>(null)
    val ubicacionActual: StateFlow<LatLng?> = _ubicacionActual

    private val _ubicationClicked = MutableStateFlow<LatLng?>(null)
    val ubicationClicked: StateFlow<LatLng?> = _ubicationClicked

    private val _positions = MutableLiveData<List<Positions>>()
    val positions: LiveData<List<Positions>> = _positions

    init {
        initViewModel()
    }

    fun initViewModel() {
        viewModelScope.launch {
            val result = getPositions.invoke()
            Log.d(logTAG, "positions: $result")
            if (result.isNotEmpty()) {
                _positions.postValue(result)
            }
        }
    }

    fun actualizarUbicacionActual(ubicacion: LatLng) {
        _ubicacionActual.value = ubicacion
    }

    fun updateLocationClicked(location: LatLng){
        _ubicationClicked.value = location
    }

}