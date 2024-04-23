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

    // try to use this helplist to currenthelp post
    private val _helpList = MutableStateFlow<List<HelpNeeded>>(emptyList())
    var helpList: StateFlow<List<HelpNeeded>> = _helpList.asStateFlow()

    private val _category = MutableStateFlow("")
    var category: StateFlow<String> = _category.asStateFlow()

    private val _helpDetailsScreenState = MutableStateFlow("")
    var helpDetailsScreenState: StateFlow<String> = _helpDetailsScreenState.asStateFlow()

    //get user post
    private val _userHelpPost = MutableStateFlow<List<HelpNeeded>>(emptyList())
    var userHelpPost: StateFlow<List<HelpNeeded>> = _userHelpPost.asStateFlow()

    private val _filteredUserHelpPost = MutableStateFlow<List<HelpNeeded>>(emptyList())
    val filteredUserHelpPost: StateFlow<List<HelpNeeded>> = _filteredUserHelpPost

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
        val userEmail = Firebase.auth.currentUser?.email
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
                                "requestPostDate" to dateNow,
                                "userEmail" to userEmail
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
                                        requestPostDate = doc.get("requestPostDate").toString(),
                                        userEmail = doc.get("userEmail").toString()
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
    fun filterUserHelpPosts(userID: String) {
        viewModelScope.launch {
            try {
                // Make sure to fetch all posts if not already done
                if (_helpList.value.isEmpty()) {
                    getAllHelpRequests()
                }
                val filteredPosts = _helpList.value.filter { it.userId == userID }
                if (filteredPosts.isEmpty()) {
                    Log.d("HelpViewModel", "no post found for this userID: $userID")
                }else {
                    _filteredUserHelpPost.value = filteredPosts
                    _userHelpPost.value = filteredPosts
                    Log.d("HelpViewModel", "Filtered posts for user ID: $userID")
                }
            }catch (e: Exception) {
                Log.e("HelpViewModel", "Error filtering posts for user ID: $userID", e)
                // Handle the error by updating a UI state or notifying the user
            }

            //_userHelpPost.value = _helpList.value.filter { it.userId == userID }
            Log.d("HelpViewModel", "Filtered posts for user ID: $userID")
        }
    }
    fun deleteUserHelpPost(postId: String, userId: String) {
        //double check if correct login user
        val currentUserEmail = Firebase.auth.currentUser?.email
        if (currentUserEmail != null )
        {
            db.collection("helpDetails").document(postId).get()
                .addOnSuccessListener {documentSnapshot ->
                    val helpPost = documentSnapshot.toObject(HelpNeeded::class.java)
                    //make sure it is login user
                    if(helpPost != null && helpPost.userId == userId){
                        documentSnapshot.reference.delete().addOnSuccessListener {
                            Log.e("HelpViewModel", "Post delete successfully.")
                            filterUserHelpPosts(userId)
                        }
                            .addOnFailureListener{e ->
                                Log.w("HelpViewModel", "Error deleting post", e)
                            }
                    }else  {
                        Log.w("HelpViewModel", "The post is not yours")
                    }
                }.addOnFailureListener{
                    Log.w("helpViewModel", "User need to log in or no correct email", it)
                }
        }else {
            Log.w("HelpViewModel", "User is not logged in or email error.")
        }
    }
}



    //delete users post
    /*
    fun deleteUserHelpPost(postId: String, userId: String) {
        //double check if correct login user
        val currentUserEmail = Firebase.auth.currentUser?.email
        if (currentUserEmail != null )
        {
            db.collection("helpDetails").document(postId).get()
                .addOnSuccessListener {documentSnapshot ->
                    val helpPost = documentSnapshot.toObject(HelpNeeded::class.java)
                    //make sure it is login user
                    if(helpPost != null && helpPost.userId == userId){
                        documentSnapshot.reference.delete().addOnSuccessListener {
                            Log.e("HelpViewModel", "Post delete successfully.")
                                getCurrentUserPost(currentUserEmail)
                        }
                            .addOnFailureListener{e ->
                                Log.w("HelpViewModel", "Error deleting post", e)
                            }
                    }else  {
                        Log.w("HelpViewModel", "The post is not yours")
                    }
                }.addOnFailureListener{
                    Log.w("helpViewModel", "User need to log in or no correct email", it)
                }
        }else {
            Log.w("HelpViewModel", "User is not logged in or email error.")
        }
}

*/





