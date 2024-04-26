package com.example.helpersapp.model

data class HelperInfo(
    val fullName: String = "",
    val about: String = "",
    val category: String =  "" ,
    val details: String = "",
    val experience: String = "",
    val username: String = "",
    val rating: List<Int> = emptyList()
    //var profilePictureByteArray: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HelperInfo

        if (fullName != other.fullName) return false
        if (about != other.about) return false
        if (category != other.category) return false
        if (details != other.details) return false
        if (experience != other.experience) return false
        if (username != other.username) return false


        return true
    }

    override fun hashCode(): Int {
        var result = fullName.hashCode()
        result = 31 * result + about.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + details.hashCode()
        result = 31 * result + experience.hashCode()
        result = 31 * result + username.hashCode()
        //result = 31 * result + (profilePictureByteArray?.contentHashCode() ?: 0)
        return result
    }
}