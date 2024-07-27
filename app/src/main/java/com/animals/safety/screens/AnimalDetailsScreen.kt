package com.animals.safety.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animals.safety.R
import com.animals.safety.data.Animal
import com.animals.safety.data.Breed
import com.animals.safety.ui.theme.AimantsDanimauxTheme
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalDetailsScreen(
  modifier: Modifier = Modifier,
  animal: Animal,
  onBackClick: () -> Unit,
) {
  Scaffold(
    modifier = modifier,
    topBar = {
      TopAppBar(
        title = {
          Text(stringResource(id = R.string.details_fragment_label))
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
  ) { contentPadding ->
    AnimalDetails(
      modifier = modifier.padding(contentPadding),
      animal = animal,
    )
  }
}

@Composable
private fun AnimalDetails(
  modifier: Modifier = Modifier,
  animal: Animal
)
{
  //TODO: A compléter
  Column (
    modifier = Modifier
      .fillMaxSize()
      .background(color = Color(0xFFF3FBFF))
  ) {
    Row (
      modifier = Modifier
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.Center
    ) {

      Box(
        modifier = modifier
          // .height(height)
          .fillMaxWidth(),
        //  .background(White, shape = shape),
        contentAlignment = Alignment.BottomStart
      ) {
        Image(
          painterResource(animal.breed.cover),
          contentDescription = animal.name,
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .padding(10.dp),
        )
        Text(
          text = animal.name,
          color = Color.White,
          fontSize = 30.sp,
          modifier = Modifier
            .padding(20.dp)
        )
      }
    }

    Row (
      modifier = Modifier
        .fillMaxWidth()
        .padding(top=50.dp),
      horizontalArrangement = Arrangement.SpaceAround
    ) {
      Column (
        verticalArrangement = Arrangement.spacedBy(20.dp)
      ){
        Icon(
          painterResource(R.drawable.ic_age),
          contentDescription = "",
          modifier = Modifier
            .size(70.dp)
        )
        Text(stringResource(R.string.value_age, animal.age))
      }

      Column (
        verticalArrangement = Arrangement.spacedBy(20.dp)
      ) {
        Icon(
          painterResource(R.drawable.ic_weight),
          contentDescription = "",
          modifier = Modifier
            .size(70.dp)
        )
        Text(stringResource(R.string.value_weight, animal.weight))
      }

    }
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(top=50.dp),
      horizontalArrangement = Arrangement.Center
    ) {
      Column (
        verticalArrangement = Arrangement.spacedBy(20.dp)
      ){
        Icon(
          painterResource(R.drawable.ic_height),
          contentDescription = "",
          modifier = Modifier
            .size(70.dp)
        )
        Text(stringResource(R.string.value_height, animal.height))
      }
    }
  }


}

@Preview(showBackground = true)
@Composable
private fun AnimalDetailsPreview() {
  AimantsDanimauxTheme(dynamicColor = false) {
    AnimalDetails(
      animal = Animal(UUID.randomUUID(),"Milou", Breed.DOG, 6, 23.2f, 42.4f),
    )
  }
}