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

    //get user post
    private val _userHelpPost = MutableStateFlow<List<HelpNeeded>>(emptyList())
    var userHelpPost: StateFlow<List<HelpNeeded>> = _userHelpPost.asStateFlow()

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

    //add to get help post from login user
    fun getCurrentUserPost(userID: String){
        //val userID = Firebase.auth.currentUser
        viewModelScope.launch {
            try {
            db.collection("helpDetails")
                .whereEqualTo("userID", userID)
                .get()
                .addOnSuccessListener{
                    querySnapshot ->
                    Log.d("HelpViewModel", "Successfully fetched posts, count: ${querySnapshot.size()}")
                    val posts = mutableListOf<HelpNeeded>()
                    for(document in querySnapshot.documents) {
                        document.toObject(HelpNeeded::class.java)?.let {
                            it.id = document.id
                            posts.add(it)
                        }?: Log.e("HelpViewModel", "Error parsing document: ${document.id}")
                    /*
                    val posts = querySnapshot.documents.mapNotNull {
                        document ->
                        document.toObject(HelpNeeded::class.java)?.apply {
                            id = document.id
                        }

                     */

                        //it.toObject(HelpNeeded::class.java)
                    }
                    _userHelpPost.value = posts
                }
                .addOnFailureListener{
                    e->
                    Log.e("HelpViewModel", "We can not find your post", e)
                    _userHelpPost.value = emptyList()
                }
        }catch(e: Exception) {
                Log.e("HelpViewModel", "unhandle exception in fetch user post", e)
            }
    }



    //delete users post
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
    }


}



