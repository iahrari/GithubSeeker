package ir.iahrari.githubseeker.service.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class License(
    val key: String?,
    val name: String?,
    val url: String?,
    @SerializedName("html_url") val htmlUrl: String?,
    val description: String?,
    val permissions: List<String>?,
    val conditions: List<String>?,
    val limitations: List<String>?
):Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeString(name)
        parcel.writeString(url)
        parcel.writeString(htmlUrl)
        parcel.writeString(description)
        parcel.writeStringList(permissions)
        parcel.writeStringList(conditions)
        parcel.writeStringList(limitations)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<License> {
        override fun createFromParcel(parcel: Parcel): License {
            return License(parcel)
        }

        override fun newArray(size: Int): Array<License?> {
            return arrayOfNulls(size)
        }
    }
}