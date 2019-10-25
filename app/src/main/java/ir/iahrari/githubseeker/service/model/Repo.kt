package ir.iahrari.githubseeker.service.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Repo(
    val id: Int,
    val name: String?,
    val description: String?,
    val language: String?,
    val private: Boolean,
    @SerializedName("full_name") val path: String?,
    @SerializedName("html_url") val hURL: String?,
    @SerializedName("git_url") val gitURl: String?,
    @SerializedName("ssh_url") val sshURL: String?,
    @SerializedName("clone_url") val cloneURL: String?,
    val size: Int,
    val owner: User
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readParcelable(User::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(language)
        parcel.writeByte(if (private) 1 else 0)
        parcel.writeString(path)
        parcel.writeString(hURL)
        parcel.writeString(gitURl)
        parcel.writeString(sshURL)
        parcel.writeString(cloneURL)
        parcel.writeInt(size)
        parcel.writeParcelable(owner, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Repo> {
        override fun createFromParcel(parcel: Parcel): Repo {
            return Repo(parcel)
        }

        override fun newArray(size: Int): Array<Repo?> {
            return arrayOfNulls(size)
        }
    }
}