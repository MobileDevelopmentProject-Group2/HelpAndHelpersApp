package com.example.helpersapp.ui.components

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate

@SuppressLint("RememberReturnType", "SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePicker(onDateSelected: (String) -> Unit ) {
    val currentDate = LocalDate.now()
    val selectedDate = remember { mutableStateOf(LocalDate.now()) }
        CalendarDialog(
            state = rememberUseCaseState(visible = true, true, onCloseRequest = { } ),
            config = CalendarConfig(
                yearSelection = true,
                style = CalendarStyle.MONTH,
                boundary = currentDate..currentDate.plusYears(1)
            ),
            selection = CalendarSelection.Date(
                selectedDate = selectedDate.value
            ) { newDate ->
                selectedDate.value = newDate
                Log.d("DatePicker", "Selected date: $newDate")
                onDateSelected(newDate.toString())
            }
        )
}
