package ir.iahrari.githubseeker.service.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Content(
    val name: String,
    val path: String,
    val type: String,
    val url: String?,
    val size: Int,
    val content: String,
    @SerializedName("html_url") val hUrl: String,
    @SerializedName("download_url") val downloadURl: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    val dir = "dir"

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(path)
        parcel.writeString(type)
        parcel.writeString(url)
        parcel.writeInt(size)
        parcel.writeString(content)
        parcel.writeString(hUrl)
        parcel.writeString(downloadURl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Content> {
        override fun createFromParcel(parcel: Parcel): Content {
            return Content(parcel)
        }

        override fun newArray(size: Int): Array<Content?> {
            return arrayOfNulls(size)
        }
    }
}