package com.mymap.geomaticapp.ui.formlocation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mymap.geomaticapp.data.api.request.AddPositionRequest
import com.mymap.geomaticapp.domain.usecases.AddPositionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormLocationViewModel @Inject constructor(
    private val addPositionUseCase: AddPositionUseCase
) : ViewModel() {

    private val logTAG = FormLocationViewModel::class.java.simpleName

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    private val _type = MutableLiveData<Int>()
    val type: LiveData<Int> = _type

    private val _isAddLocationEnable = MutableLiveData<Boolean>()
    val isAddLocationEnable: LiveData<Boolean> = _isAddLocationEnable

    private val _uiState =
        MutableStateFlow<UiState>(UiState.Start)
    val uiState = _uiState.asStateFlow()


    fun addPosition(request: AddPositionRequest) {
        viewModelScope.launch {
            _uiState.update { UiState.Loading }
            addPositionUseCase(request = request).collect { result ->
                if (result){
                    _uiState.update { UiState.Success }
                } else {
                    _uiState.update { UiState.Error }
                }
            }
        }
    }

    fun onRequestDataChanged(name: String, description: String, type: Int){
        _name.value = name
        _description.value = description
        _type.value = type
        _isAddLocationEnable.value = enableAddLocation(name = name, description = description, type = type)
    }

    private fun enableAddLocation(name: String, description: String, type: Int): Boolean =
        name.isNotEmpty() && description.isNotEmpty() && type != -1

    fun resetUiState() {
        _uiState.update { UiState.Start }
        _name.value = ""
        _description.value = ""
        _type.value = 1
        _isAddLocationEnable.value = false
    }

}