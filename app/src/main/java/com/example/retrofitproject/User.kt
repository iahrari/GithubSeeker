package com.example.retrofitproject

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login") val username: String,
    @SerializedName("avatar_url") val avatar: String?,
    @SerializedName("gravatar_url") val gravatar: String?,
    val name: String,
    @SerializedName("html_url") val url: String,
     val blog: String
): Parcelable {
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(avatar)
        parcel.writeString(gravatar)
        parcel.writeString(name)
        parcel.writeString(url)
        parcel.writeString(blog)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}