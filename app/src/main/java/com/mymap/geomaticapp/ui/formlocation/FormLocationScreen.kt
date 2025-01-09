package com.mymap.geomaticapp.ui.formlocation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.mymap.geomaticapp.data.api.request.AddPositionRequest
import com.mymap.geomaticapp.data.api.request.CoordinatesRequest
import com.mymap.geomaticapp.model.TypesLocation
import com.mymap.geomaticapp.ui.dialogs.PositionCreatedDialog
import com.mymap.geomaticapp.ui.theme.AllWhite
import com.mymap.geomaticapp.ui.theme.DarkAstral
import com.mymap.geomaticapp.ui.theme.DefaultGrayContainer
import com.mymap.geomaticapp.ui.theme.DisableContainer
import com.mymap.geomaticapp.ui.theme.DisableContent
import com.mymap.geomaticapp.ui.theme.Tradewind
import com.mymap.geomaticapp.ui.theme.White

@Composable
fun FormLocationScreen(
    backPressed: () -> Unit,
    lat: String?,
    lon: String?,
    viewModel: FormLocationViewModel
) {

    val name: String by viewModel.name.observeAsState(initial = "")

    val description: String by viewModel.description.observeAsState(initial = "")

    val type: Int by viewModel.type.observeAsState(initial = -1)

    val isButtonEnabled by viewModel.isAddLocationEnable.observeAsState(initial = false)

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    var showDialogSuccess by rememberSaveable {
        mutableStateOf(false)
    }

    val uiState by produceState<UiState>(
        initialValue = UiState.Start,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect {
                value = it
            }
        }
    }

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(AllWhite), topBar = {
        MyTopAppBar(backPressed)
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
                .background(AllWhite),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyTextField(hint = "Nombre de la posición", value = name, isEnabled = true) {
                viewModel.onRequestDataChanged(name = it, description = description, type = type)
            }
            MyTextField(hint = "Latitud de la posición", value = lat ?: "", isEnabled = false) {

            }
            MyTextField(hint = "Longitud de la posición", value = lon ?: "", isEnabled = false) {

            }
            DescriptionTextField(value = description) {
                viewModel.onRequestDataChanged(name = name, description = it, type = type)

            }
            SpinnerDemo {
                viewModel.onRequestDataChanged(name = name, description = description, type = it)
            }
            MyButton(text = "Crear posición", isEnabled = isButtonEnabled) {
                if (lat != null && lon != null) {
                    val request = AddPositionRequest(
                        coordinates = CoordinatesRequest(
                            lat = lat.toDouble(),
                            long = lon.toDouble()
                        ),
                        name = name,
                        description = description,
                        type = type
                    )
                    Log.d("FormLocationScreen", "request: $request")
                    viewModel.addPosition(
                        request = request
                    )
                } else {
                    //Do Something
                }
            }

            when(uiState){
                UiState.Loading -> {

                }
                UiState.Success -> {
                    showDialogSuccess = true
                    PositionCreatedDialog(show = showDialogSuccess, onDismiss = {
                        viewModel.resetUiState()
                        backPressed()
                        showDialogSuccess = false
                    })
                }
                UiState.Error -> {
                    //Mostrar toast error
                }
            }

        }
    }
}

@Composable
fun MyButton(text: String, isEnabled: Boolean, onClickedButton: () -> Unit) {
    Button(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = DisableContainer,
            disabledContentColor = DisableContent,
            containerColor = Tradewind,
            contentColor = White
        ),
        onClick = {
            onClickedButton()
        }) {
        Text(text = text)
    }
}

@Composable
fun DescriptionTextField(value: String, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        value = value,
        onValueChange = { onTextChanged(it) },
        placeholder = { Text(text = "Escribe una descripción...") },
        label = { Text(text = "Escribe una descripción...") },
        maxLines = 5,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = White,
            focusedBorderColor = DarkAstral,
            focusedLabelColor = DarkAstral,
            focusedPlaceholderColor = DarkAstral,
            focusedTextColor = DarkAstral,
            unfocusedContainerColor = DefaultGrayContainer,
            unfocusedBorderColor = DefaultGrayContainer,
            unfocusedLabelColor = DisableContent,
            unfocusedPlaceholderColor = DisableContent,
            unfocusedTextColor = DisableContent,
            disabledContainerColor = DisableContainer,
            disabledBorderColor = DisableContainer,
            disabledLabelColor = DisableContent,
            disabledPlaceholderColor = DisableContent,
            disabledTextColor = DisableContent
        )
    )
}

@Composable
fun MyTextField(hint: String, value: String, isEnabled: Boolean, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        value = value,
        enabled = isEnabled,
        onValueChange = { onTextChanged(it) },
        maxLines = 1,
        label = {
            Text(text = hint)
        },
        placeholder = {
            Text(text = hint)
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = White,
            focusedBorderColor = DarkAstral,
            focusedLabelColor = DarkAstral,
            focusedPlaceholderColor = DarkAstral,
            focusedTextColor = DarkAstral,
            unfocusedContainerColor = DefaultGrayContainer,
            unfocusedBorderColor = DefaultGrayContainer,
            unfocusedLabelColor = DisableContent,
            unfocusedPlaceholderColor = DisableContent,
            unfocusedTextColor = DisableContent,
            disabledContainerColor = DisableContainer,
            disabledBorderColor = DisableContainer,
            disabledLabelColor = DisableContent,
            disabledPlaceholderColor = DisableContent,
            disabledTextColor = DisableContent
        )

    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerDemo(onOptionChanged: (Int) -> Unit) {
    val items = TypesLocation.getListOptions()

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(items[0]) }

    ExposedDropdownMenuBox(
        modifier = Modifier.padding(vertical = 12.dp),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedOption.nameLocation,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            label = { Text("Selecciona el tipo de localización") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedContainerColor = White,
                focusedLabelColor = DarkAstral,
                focusedPlaceholderColor = DarkAstral,
                focusedTextColor = DarkAstral,
                unfocusedContainerColor = DefaultGrayContainer,
                unfocusedLabelColor = DisableContent,
                unfocusedPlaceholderColor = DisableContent,
                unfocusedTextColor = DisableContent,
                cursorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )

        ExposedDropdownMenu(
            modifier = Modifier.padding(horizontal = 16.dp),
            containerColor = White,
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option.nameLocation,
                            color = DisableContent
                        )
                    },
                    onClick = {
                        selectedOption = option
                        onOptionChanged(option.typeLocation)
                        expanded = false // Close the menu
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(onClickIcon: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Agregar Posición"
            )
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Tradewind,
            titleContentColor = AllWhite,
            navigationIconContentColor = AllWhite
        ),
        navigationIcon = {
            IconButton(onClick = { onClickIcon() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewComponents() {
    Column {
        MyTextField(hint = "Nombre de la localización", value = "", isEnabled = true) {

        }
        MyTextField(hint = "Latitud de la ubicación", value = "", isEnabled = false) {

        }
        DescriptionTextField(value = "") {

        }

        SpinnerDemo {

        }

        MyButton(text = "Test 1", isEnabled = true) {

        }

        MyButton(text = "Test 2", isEnabled = false) {

        }
    }

}


