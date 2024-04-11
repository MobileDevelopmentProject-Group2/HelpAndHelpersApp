package com.example.helpersapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpersapp.R
import com.example.helpersapp.model.HelpNeeded
import com.example.helpersapp.viewModel.HelpViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ListAllHelpNeeded(
    helpList: List<HelpNeeded>,
    helpViewModel: HelpViewModel,
    navController: NavController
) {
    Column {
        helpList.forEach {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp)
                    .clickable(
                        onClick = {
                            helpViewModel.setNewHelpNeeded(it)
                            helpViewModel.setHelpDetailsScreenState("contact")
                            navController.navigate("helpDetails")
                        }
                    ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                ) {
                    if (it.category == "tutor") {
                        Image(
                            painter = painterResource(id = R.drawable.reading),
                            contentDescription = "reading",
                            modifier = Modifier.size(35.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.baby_face_icon),
                            contentDescription = "child",
                            modifier = Modifier.size(35.dp)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .weight(1f)
                    ) {
                        Text(text = it.workDetails)
                    }
                    Column(
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .weight(1f)
                    ) {
                        Text(text = "Date: \n${it.date}")
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(text = "Payment: \n${it.priceRange.endInclusive.toInt()} € - ${it.priceRange.start.toInt()} €")

                    }
                }
                Text(
                    text = "Request posted: ${formatDateValue(it.requestPostDate)}",
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}