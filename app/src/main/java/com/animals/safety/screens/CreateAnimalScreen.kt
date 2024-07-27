package com.animals.safety.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.animals.safety.R
import com.animals.safety.data.Animal
import com.animals.safety.data.AnimalData
import com.animals.safety.data.Breed
import com.animals.safety.ui.theme.AimantsDanimauxTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAnimalScreen(
  modifier: Modifier = Modifier,
  onBackClick: () -> Unit,
  onSaveClick:() -> Unit
) {
  val scope = rememberCoroutineScope()
  val context = LocalContext.current
  val snackbarHostState = remember { SnackbarHostState() }
  
  val name = rememberSaveable { mutableStateOf("") }
  val breed = rememberSaveable { mutableStateOf(Breed.entries[0]) }
  val age = rememberSaveable { mutableStateOf("") }
  val weight = rememberSaveable { mutableStateOf("") }
  val height = rememberSaveable { mutableStateOf("") }

  Scaffold(
    modifier = modifier,
    topBar = {
      TopAppBar(
        title = {
          Text(stringResource(id = R.string.create_fragment_label))
        },
        navigationIcon = {
          IconButton(onClick = {
            onBackClick()
          }) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = stringResource(id = R.string.contentDescription_go_back)
            )
          }
        }
      )
    },
    snackbarHost = {
      SnackbarHost(hostState = snackbarHostState)
    },
    floatingActionButtonPosition = FabPosition.Center,
    floatingActionButton = {
      ExtendedFloatingActionButton(
        onClick = {
          if (verifyAndCreateAnimal(
              name.value,
              breed.value,
              age.value,
              weight.value,
              height.value,
              snackbarHostState,
              scope,
              context
            )
          ) {
            onSaveClick()
          }
        }
      ) {
        Text(
          text = stringResource(id = R.string.action_save)
        )
      }
    }
  ) { contentPadding ->
    CreateAnimal(
      modifier = Modifier.padding(contentPadding),
      name = name.value,
      onNameChanged = { name.value = it },
      breed = breed.value,
      onBreedChanged = { breed.value = it },
      age = age.value,
      onAgeChanged = { age.value = it },
      weight = weight.value,
      onWeightChanged = { weight.value = it },
      height = height.value,
      onHeightChanged = { height.value = it }
    )
  }
}

fun verifyAndCreateAnimal(
  name: String,
  breed: Breed,
  age: String,
  weight: String,
  height: String,
  snackbarHostState: SnackbarHostState,
  scope: CoroutineScope,
  context: Context
): Boolean
{
  if (name.isBlank()) {
    scope.launch {
      snackbarHostState.showSnackbar(context.getString(R.string.issue_name_empty))
    }

    return false;
  }

  val animalAge: Int;
  try {
    animalAge = age.toInt()
  } catch (e: NumberFormatException) {
    scope.launch {
      snackbarHostState.showSnackbar(context.getString(R.string.issue_invalid_age))
    }

    return false;
  }

  val animalWeight: Float;
  try {
    animalWeight = weight.toFloat()
  } catch (e: NumberFormatException) {
    scope.launch {
      snackbarHostState.showSnackbar(context.getString(R.string.issue_invalid_weight))
    }

    return false;
  }

  val animalHeight: Float;
  try {
    animalHeight = height.toFloat()
  } catch (e: NumberFormatException) {
    scope.launch {
      snackbarHostState.showSnackbar(context.getString(R.string.issue_invalid_height))
    }

    return false;
  }

  AnimalData.animals.add(
    Animal(
      UUID.randomUUID(),
      name,
      breed,
      animalAge,
      animalWeight,
      animalHeight
    )
  )

  return true
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateAnimal(
  modifier: Modifier = Modifier,
  name: String,
  onNameChanged: (String) -> Unit,
  age: String,
  onAgeChanged: (String) -> Unit,
  weight: String,
  onWeightChanged: (String) -> Unit,
  height: String,
  onHeightChanged: (String) -> Unit,
  breed: Breed,
  onBreedChanged: (Breed) -> Unit
) {

  val context = LocalContext.current

  val scrollState = rememberScrollState()

  var expanded by remember { mutableStateOf(false) }
  MaterialTheme(
    colorScheme = MaterialTheme.colorScheme.copy(surface = Color(0xFFF3FBFF))
  ) {
    Column(
      modifier = modifier
        .background(color = Color(0xFFF3FBFF))
        .padding(bottom = 88.dp, top = 16.dp, start = 16.dp, end = 16.dp)
        .fillMaxSize()
        .fillMaxWidth()
        .verticalScroll(scrollState),
      verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
      //TODO: A compléter
      OutlinedTextField(
        value = name,
        onValueChange = {
          onNameChanged(it)
        },
        //label = { Text(text = stringResource(id = R.string.hint_name)) },
        placeholder = { Text(text = stringResource(id = R.string.hint_name)) },
        maxLines = 1,
      )

      ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
          expanded = !expanded
        }

      ) {
        OutlinedTextField(
          value = stringResource(breed.translatedName),
          label = { Text(text = stringResource(id = R.string.hint_breed)) },
          onValueChange = {},
          readOnly = true,
          trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
          modifier = Modifier.menuAnchor(),
          maxLines = 1
        )

        ExposedDropdownMenu(
          expanded = expanded,
          onDismissRequest = { expanded = false }
        ) {
          Breed.entries.forEach { item ->
            DropdownMenuItem(
              text = { Text(text = stringResource(item.translatedName)) },
              onClick = {
                onBreedChanged(item)
                expanded = false
                Toast.makeText(context, item.translatedName, Toast.LENGTH_SHORT).show()
              }
            )
          }
        }
      }

      OutlinedTextField(
        value = age,
        onValueChange = {
          onAgeChanged(it)
        },
        //label = { Text(text = stringResource(id = R.string.hint_age)) },
        placeholder = { Text(text = stringResource(id = R.string.hint_age)) },
        maxLines = 1
      )

      OutlinedTextField(
        value = weight,
        onValueChange = {
          onWeightChanged(it)
        },
        //label = { Text(text = stringResource(id = R.string.hint_weight)) },
        placeholder = { Text(text = stringResource(id = R.string.hint_weight)) },
        maxLines = 1
      )

      OutlinedTextField(
        value = height,
        onValueChange = {
          onHeightChanged(it)
        },
        //label = { Text(text = stringResource(id = R.string.hint_height)) },
        placeholder = { Text(text = stringResource(id = R.string.hint_height)) },
        maxLines = 1
      )

    }
  }
}

@Preview(showBackground = true)
@Composable
private fun CreateAnimalPreview() {
  AimantsDanimauxTheme(dynamicColor = false) {
    CreateAnimal(
      name = "Milou",
      onNameChanged = { },
      age = "6",
      onAgeChanged = { },
      weight = "473.6",
      onWeightChanged = { },
      height = "14.7",
      onHeightChanged = { },
      breed = Breed.entries[0],
      onBreedChanged = { }
    )
  }
}