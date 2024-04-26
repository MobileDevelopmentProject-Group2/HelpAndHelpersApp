package com.example.helpersapp.viewModel

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helpersapp.model.HelpNeeded
import com.example.helpersapp.ui.components.formatDateValue
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.Calendar
import java.util.TimeZone

class HelpViewModel: ViewModel() {

    val db = Firebase.firestore

    private val _newHelpNeeded = MutableStateFlow(HelpNeeded())
    var newHelpNeeded: StateFlow<HelpNeeded> = _newHelpNeeded.asStateFlow()

    private val _helpList = MutableStateFlow<List<HelpNeeded>>(emptyList())
    var helpList: StateFlow<List<HelpNeeded>> = _helpList.asStateFlow()

    private val _category = MutableStateFlow("")
    var category: StateFlow<String> = _category.asStateFlow()

    private val _helpDetailsScreenState = MutableStateFlow("")
    var helpDetailsScreenState: StateFlow<String> = _helpDetailsScreenState.asStateFlow()

    private val _filteredUserHelpPost = MutableStateFlow<List<HelpNeeded>>(emptyList())
    val filteredUserHelpPost: StateFlow<List<HelpNeeded>> = _filteredUserHelpPost.asStateFlow()

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
    fun emptyFilteredUserHelpPost() {
        _filteredUserHelpPost.value= emptyList()
        Log.d("HelpViewModel", "Filtered posts emptied: ${_filteredUserHelpPost.value}")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addNewHelpToCollection() {
        val timeUTC = OffsetDateTime.now(ZoneOffset.UTC)
        val user = Firebase.auth.currentUser
        val userEmail = Firebase.auth.currentUser?.email
        val userID = Firebase.auth.currentUser?.uid

        viewModelScope.launch {
            try {
                user?.let {
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
                                "requestPostDate" to timeUTC.toString(),
                                "userEmail" to userEmail
                            )
                        )
                        .addOnSuccessListener {
                            val documentId = it.id
                            Log.d("HelpViewModel", "Help added successfully, document id: $documentId")
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
    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllHelpRequests() {
        val user = Firebase.auth.currentUser
        viewModelScope.launch {
            try {
                user?.let {
                    db.collection("helpDetails")
                        .get()
                        .addOnSuccessListener {
                            val helpListTemporary = mutableListOf<HelpNeeded>()
                            val timeNow = System.currentTimeMillis()
                            println("Time now: $timeNow")
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
                                        requestPostDate = doc.get("requestPostDate").toString(),
                                        userEmail = doc.get("userEmail").toString(),
                                        helpPostId = doc.id
                                    )
                                )
                            }
                            // Sort the list by requestPostDate
                            helpListTemporary.sortByDescending { helpNeeded -> helpNeeded.requestPostDate}
                            _helpList.value = helpListTemporary
                            Log.d("HelpViewModel", "Help list fetched successfully")
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
    @RequiresApi(Build.VERSION_CODES.O)
    fun filterUserHelpPosts(userID: String) {
        viewModelScope.launch {
            try {
                // Make sure to fetch all posts if not already done
                getAllHelpRequests()

                val filteredPosts = _helpList.value.filter { it.userId == userID }
                if (filteredPosts.isEmpty()) {
                    _filteredUserHelpPost.value = emptyList()
                    Log.d("HelpViewModel", "no post found for this userID: $userID")
                }else {
                    _filteredUserHelpPost.value = filteredPosts
                    //_userHelpPost.value = filteredPosts
                    Log.d("HelpViewModel", "Filtered posts for user ID: $userID")
                    Log.d("HelpViewModel", "Filtered posts: ${_filteredUserHelpPost.value}")
                }
            }catch (e: Exception) {
                Log.e("HelpViewModel", "Error filtering posts for user ID: $userID", e)
                // Handle the error by updating a UI state or notifying the user
            }

            //_userHelpPost.value = _helpList.value.filter { it.userId == userID }
            Log.d("HelpViewModel", "Filtered posts for user ID: $userID")
        }
    }
@SuppressLint("SuspiciousIndentation")
    fun deleteHelpRequests(id: String) {
        viewModelScope.launch {
            try {
                db.collection("helpDetails")
                    .whereEqualTo("userId", id)
                    .get()
                    .addOnSuccessListener {
                        Log.d("HelpViewModel", "Help request found for user: ${it.documents.size}")
                        if (it.documents.isEmpty()) {
                            Log.d("HelpViewModel", "No help request found for user")
                            return@addOnSuccessListener
                        }
                        val batch = db.batch()
                        it.documents.forEach { doc ->
                            batch.delete(db.collection("helpDetails").document(doc.id))
                        }
                        batch.commit()
                            .addOnSuccessListener {
                                Log.d("HelpViewModel", "Help request deleted successfully for user")
                            }
                            .addOnFailureListener {
                                Log.e("HelpViewModel", it.message.toString())
                            }
                    }
                    .addOnFailureListener {
                        Log.e("HelpViewModel", it.message.toString())
                    }
            } catch (e: Exception) {
                Log.d("HelpViewModel", e.message.toString())
            }
        }
    }
    fun deleteUserHelpPost(postId: String) {
        viewModelScope.launch() {
            try {
                db.collection("helpDetails")
                    .document(postId)
                    .delete()
                    .addOnSuccessListener {
                        Log.d("HelpViewModel", "Help request deleted successfully")
                    }
                    .addOnFailureListener {
                        Log.e("HelpViewModel", it.message.toString())
                    }
            } catch (e: Exception) {
                Log.d("HelpViewModel", e.message.toString())
            }
        }
    }
}

