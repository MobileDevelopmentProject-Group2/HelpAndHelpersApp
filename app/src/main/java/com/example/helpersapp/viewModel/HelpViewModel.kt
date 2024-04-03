package com.example.helpersapp.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helpersapp.model.HelpNeeded
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HelpViewModel: ViewModel()  {

    val db = Firebase.firestore
    //val user = Firebase.auth.currentUser
    val user = "username4"

    private val _newHelpNeeded = MutableStateFlow(HelpNeeded())
    val newHelpNeeded: StateFlow<HelpNeeded> = _newHelpNeeded.asStateFlow()

    private val _helpList = MutableStateFlow<List<HelpNeeded>>(emptyList())
    val helpList: StateFlow<List<HelpNeeded>> = _helpList.asStateFlow()

    fun changeWorkDetails(newWorkDetails: String) {
        _newHelpNeeded.value = _newHelpNeeded.value.copy(workDetails = newWorkDetails)
    }
    fun changePriceRange(newPriceRange: ClosedFloatingPointRange<Float>) {
        _newHelpNeeded.value = _newHelpNeeded.value.copy(priceRange = newPriceRange)
    }
    fun changePostalCode(newPostalCode: String) {
        _newHelpNeeded.value = _newHelpNeeded.value.copy(postalCode = newPostalCode)
    }
    fun changeTime(newTime: String) {
        _newHelpNeeded.value = _newHelpNeeded.value.copy(time = newTime)
    }

    fun addNewHelpToCollection() {
        viewModelScope.launch {
            try {
                user?.let { user ->
                    db.collection("helpDetails")
                        .document(user)
                        .set(mapOf(
                            "category" to _newHelpNeeded.value.category,
                            "workDetails" to _newHelpNeeded.value.workDetails,
                            "date" to _newHelpNeeded.value.date,
                            "time" to _newHelpNeeded.value.time,
                            "priceRange" to _newHelpNeeded.value.priceRange,
                            "postalCode" to _newHelpNeeded.value.postalCode
                        ))
                        .addOnSuccessListener {
                            Log.d("HelpViewModel", "Help added successfully")
                            //Toast.makeText(null, "Help added successfully", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Log.e("HelpViewModel", it.message.toString())
                        }
                }
            } catch (e: Exception) {
                Log.e("HelpViewModel", e.message.toString())
                Toast.makeText(null, "Error adding help", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun getAllHelpRequests() {
        viewModelScope.launch {
            try {
                user?.let { user ->
                    db.collection("helpDetails")
                        .get()
                        .addOnSuccessListener {
                            Log.d("HelpViewModel", "Help request list fetched successfully")
                            it.documents.forEach { help ->
                                Log.d("HelpViewModel", help.get("workDetails").toString())
                            }
                        }
                        .addOnFailureListener {
                            Log.e("HelpViewModel", it.message.toString())
                        }
                }
            } catch (e: Exception) {
                Log.e("HelpViewModel", e.message.toString())
            }
        }
    }
}