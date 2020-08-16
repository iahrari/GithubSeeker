package ir.iahrari.githubseeker.service.model.trending

import com.google.gson.annotations.SerializedName

data class TrendingLang(
    @SerializedName("urlParam") val id: String,
    val name: String
){
    override fun toString(): String {
        return name
    }

    fun string(): String{
        return "TrendingLang{name: $name, id: $id}"
    }
}