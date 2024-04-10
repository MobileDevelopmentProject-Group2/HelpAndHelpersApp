package com.example.helpersapp.viewModel

import android.util.Log
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

class HelpViewModel: ViewModel() {

    val db = Firebase.firestore
    val dateNow = System.currentTimeMillis()

    private val _newHelpNeeded = MutableStateFlow(HelpNeeded())
    var newHelpNeeded: StateFlow<HelpNeeded> = _newHelpNeeded.asStateFlow()

    private val _helpList = MutableStateFlow<List<HelpNeeded>>(emptyList())
    var helpList: StateFlow<List<HelpNeeded>> = _helpList.asStateFlow()

    private val _category = MutableStateFlow("")
    var category: StateFlow<String> = _category.asStateFlow()

    private val _helpDetailsScreenState = MutableStateFlow("")
    var helpDetailsScreenState: StateFlow<String> = _helpDetailsScreenState.asStateFlow()

    fun setNewHelpNeeded(helpNeeded: HelpNeeded) {
        _newHelpNeeded.value = helpNeeded
    }
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
    fun emptyNewHelpNeeded() {
        _newHelpNeeded.value = HelpNeeded()
    }
    fun setCategory(category: String) {
        _category.value = category
    }
    fun emptyHelpList() {
        _helpList.value = emptyList()
    }
    fun setHelpDetailsScreenState(screenState: String) {
        _helpDetailsScreenState.value = screenState
    }
    fun addNewHelpToCollection(userID: String) {
        val user = Firebase.auth.currentUser
        Log.d("HelpViewModel", "Adding new help: ${userID}")
        viewModelScope.launch {
            try {
                user?.let { user ->
                    db.collection("helpDetails")
                        .add(
                            mapOf(
                                "category" to _newHelpNeeded.value.category,
                                "workDetails" to _newHelpNeeded.value.workDetails,
                                "date" to _newHelpNeeded.value.date,
                                "time" to _newHelpNeeded.value.time,
                                "priceRange" to _newHelpNeeded.value.priceRange,
                                "postalCode" to _newHelpNeeded.value.postalCode,
                                "userId" to userID,
                                "requestPostDate" to dateNow
                            )
                        )
                        .addOnSuccessListener {
                            Log.d("HelpViewModel", "Help added successfully") }
                        .addOnFailureListener {
                            Log.e("HelpViewModel", it.message.toString())
                        }
                }
            } catch (e: Exception) {
                Log.e("HelpViewModel", e.message.toString())
            }
        }
    }
    fun getAllHelpRequests() {
        val user = Firebase.auth.currentUser
        viewModelScope.launch {
            try {
                user?.let {
                    db.collection("helpDetails")
                        .get()
                        .addOnSuccessListener {
                            val helpListTemporary = mutableListOf<HelpNeeded>()
                            it.documents.forEach { doc ->
                                val priceRangeMap = doc.get("priceRange") as? Map<String, Any>
                                val endInclusive =
                                    priceRangeMap?.get("endInclusive") as? Double ?: 0.0
                                val start = priceRangeMap?.get("start") as? Double ?: 0.0
                                helpListTemporary.add(
                                    HelpNeeded(
                                        category = doc.get("category").toString(),
                                        workDetails = doc.get("workDetails").toString(),
                                        date = doc.get("date").toString(),
                                        time = doc.get("time").toString(),
                                        priceRange = (endInclusive.toFloat()).rangeTo(start.toFloat()),
                                        postalCode = doc.get("postalCode").toString(),
                                        userId = doc.get("userId").toString(),
                                        requestPostDate = doc.get("requestPostDate").toString()
                                    )
                                )
                            }
                            // Sort the list by requestPostDate
                            helpListTemporary.sortByDescending { helpNeeded -> helpNeeded.requestPostDate}
                            _helpList.value = helpListTemporary
                            Log.d(
                                "HelpViewModel",
                                "Help request list fetched successfully: ${_helpList.value}"
                            )
                        }
                        .addOnFailureListener {
                            Log.e("HelpFailure", it.message.toString())
                        }
                }
            } catch (e: Exception) {
                Log.e("HelpCatchError", e.message.toString())
            }
        }
    }
}


