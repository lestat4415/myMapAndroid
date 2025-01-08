package com.mymap.geomaticapp.ui.formlocation

interface UiState {
    data object Start: UiState
    data object Loading: UiState
    data object Success: UiState
    data object Error: UiState
}