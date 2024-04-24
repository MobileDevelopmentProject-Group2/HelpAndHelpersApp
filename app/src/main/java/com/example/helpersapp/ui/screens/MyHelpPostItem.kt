package com.example.helpersapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpersapp.model.HelpNeeded
import com.example.helpersapp.ui.components.ConfirmDeleteUserPostDialog
import com.example.helpersapp.ui.components.formatDateValue
import com.example.helpersapp.viewModel.HelpViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyHelpPostItem (
    userHelpPosts: List<HelpNeeded>,
    navController: NavController
) {
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }

    Column {
        userHelpPosts.forEach {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(6.dp),
                //elevation = 4.dp

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp)
                            .weight(2f)
                    ){
                        Text(
                            text = "Date: ${it.date}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = it.workDetails,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Payment: ${it.priceRange.endInclusive.toInt()} € - ${it.priceRange.start.toInt()} €",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Posted Date: ${formatDateValue(it.requestPostDate)}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Contact email:  ${(it.userEmail)}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(
                            onClick = {
                                showDeleteDialog = true
                            },
                            content = {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = "Delete post",
                                    Modifier.size(24.dp)
                                )
                            }
                        )
                        Text(
                            text = "Delete post",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    if (showDeleteDialog) {
                        ConfirmDeleteUserPostDialog(navController, it.helpPostId)
                    }
                }

            }
        }
    }

}

