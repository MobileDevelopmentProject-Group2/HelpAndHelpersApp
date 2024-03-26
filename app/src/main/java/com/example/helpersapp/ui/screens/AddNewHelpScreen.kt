package com.example.helpersapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpersapp.R
import com.example.helpersapp.ui.components.CategoryRadioButtons
import com.example.helpersapp.viewModel.HelpViewModel

@Composable
fun ShowIMage() {
    Image(
        painter = painterResource(id = R.drawable.bottom_background_with_logo),
        contentDescription = "Background image of the bottom bar",
        contentScale = ContentScale.FillWidth,
        alignment = Alignment.BottomEnd,
        modifier = Modifier
            .fillMaxSize()
            .height(200.dp)
    )
}

@Composable
fun AddNewHelpScreen(navController: NavController, helpViewModel: HelpViewModel) {
    val newHelp by helpViewModel.newHelpNeeded.collectAsState()
    val radioButtonOptions = listOf("nanny", "tutor")
    val (selectedCategory, onCategorySelected) = remember { mutableStateOf(radioButtonOptions[0])}
    var sliderPosition by remember { mutableStateOf(0f..500f) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ShowIMage()
        Column(
            modifier = Modifier
                .padding(bottom = 150.dp, start = 16.dp, end = 16.dp, top = 16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.post_a_new_help_request),
                modifier = Modifier.padding(top = 16.dp, bottom = 30.dp, start = 16.dp, end = 16.dp),
                style = MaterialTheme.typography.titleLarge,
            )
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(R.string.category))
                CategoryRadioButtons(
                    radioButtonOptions = radioButtonOptions,
                    selectedCategory = selectedCategory,
                    onCategorySelected = onCategorySelected
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.work_details))
                OutlinedTextField(
                    modifier = Modifier
                        .padding(16.dp)
                        .width(210.dp),
                    value = newHelp.workDetails,
                    onValueChange = { helpViewModel.changeWorkDetails(it) },
                    label = { Text("Add work details")},
                    shape = MaterialTheme.shapes.medium
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = stringResource(R.string.date_of_work))
                Column {
                    Button(
                        onClick = { /*TODO*/ },
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Gray.copy(alpha = 0.2f),
                            contentColor = Color.Black,
                        ),
                        border = BorderStroke(1.dp, color = Color.DarkGray),
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        Row {
                            Text(stringResource(R.string.select_date))
                            Icon(
                                imageVector = Icons.Outlined.DateRange,
                                contentDescription = null,
                                modifier = Modifier.padding(start = 40.dp)
                            )
                        }
                    }
                    if (newHelp.date != "") {
                        Text(text = stringResource(R.string.selected_date, newHelp.date))
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.time))
                OutlinedTextField(
                    modifier = Modifier
                        .padding(16.dp)
                        .width(210.dp),
                    value = newHelp.time,
                    onValueChange = { helpViewModel.changeWorkDetails(it) },
                    label = { Text("Add time of work")},
                    shape = MaterialTheme.shapes.medium
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.price_range))
                Column {
                    RangeSlider(
                        value = sliderPosition,
                        steps = 100,
                        onValueChange = { range -> sliderPosition = range },
                        valueRange = 0f..500f,
                        onValueChangeFinished = {
                            helpViewModel.changePriceRange(sliderPosition)
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .width(210.dp)
                    )
                    Text(
                        modifier = Modifier.padding(start = 25.dp),
                        text = stringResource(
                        R.string.range,
                        sliderPosition.start.toInt(),
                        sliderPosition.endInclusive.toInt()
                        )
                    )
                }
            }
            Button(
                onClick = { /*TODO*/ },
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray.copy(alpha = 0.2f),
                    contentColor = Color.Black,
                ),
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(52.dp)
                    .width(210.dp)
            ) {
                Text(text = "Post to confirm")
            }
        }
    }
}




