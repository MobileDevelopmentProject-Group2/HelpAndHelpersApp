package com.example.helpersapp.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpersapp.R
import com.example.helpersapp.ui.components.CategoryRadioButtons
import com.example.helpersapp.ui.components.DatePicker
import com.example.helpersapp.ui.components.ShowBottomImage
import com.example.helpersapp.viewModel.HelpViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddNewHelpScreen(navController: NavController, helpViewModel: HelpViewModel) {
    val newHelp by helpViewModel.newHelpNeeded.collectAsState()
    val radioButtonOptions = listOf("nanny", "tutor")
    var (selectedCategory, onCategorySelected) = remember { mutableStateOf(radioButtonOptions[0])}
    var selectedDate by remember { mutableStateOf(newHelp.date) }
    var showDatePicker by remember { mutableStateOf(false) }
    var sliderPosition by remember { mutableStateOf(newHelp.priceRange) }
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ShowBottomImage()
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
                newHelp.category = selectedCategory
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
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.date_of_work))
                Column {
                    Button(
                        onClick = { showDatePicker = true },
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        border = BorderStroke(1.dp, color = Color.DarkGray),
                        modifier = Modifier
                            .padding(20.dp)
                            .width(210.dp)
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
                }
            }
            if (showDatePicker) {
                DatePicker { date ->
                    selectedDate = date
                    Toast.makeText(
                        context,
                        "Date selected: $date",
                        Toast.LENGTH_SHORT
                    ).show()
                    showDatePicker = false
                }
            }
            newHelp.date = selectedDate

            if (newHelp.date != "") {
                Text(
                    text = stringResource(R.string.selected_date, newHelp.date),
                    modifier = Modifier
                        .padding(top = 2.dp, start = 45.dp, end = 16.dp)
                )
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
                    onValueChange = { helpViewModel.changeTime(it) },
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
                        valueRange = 0f..100f,
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Postal code")
                OutlinedTextField(
                    modifier = Modifier
                        .padding(16.dp)
                        .width(210.dp),
                    value = newHelp.postalCode.toString(),
                    onValueChange = { helpViewModel.changePostalCode(it.toString()) },
                    label = { Text("Add postal code")},
                    shape = MaterialTheme.shapes.medium,
                    //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = { navController.navigateUp() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .height(52.dp)
                        .width(150.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly

                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(text = "Back")
                    }
                }
                Button(
                    onClick = {
                        if (newHelp.workDetails != "" && newHelp.date != "" && newHelp.time != "" && newHelp.priceRange != 0f..500f && newHelp.category != "" && newHelp.postalCode != null) {
                            newHelp.date = selectedDate
                            navController.navigate("helpDetails")
                        } else {
                            Toast.makeText(
                                navController.context,
                                "Please fill in all the fields",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .height(52.dp)
                        .width(150.dp)
                ) {
                    Text(text = "Post to confirm")
                }
            }

        }
    }
}




