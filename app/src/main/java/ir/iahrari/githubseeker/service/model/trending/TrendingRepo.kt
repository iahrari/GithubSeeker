package ir.iahrari.githubseeker.service.model.trending

import ir.iahrari.githubseeker.service.model.Repo
import ir.iahrari.githubseeker.service.model.User
import kotlinx.android.parcel.Parcelize

data class TrendingRepo(
    val author: String,
    val name: String,
    val avatar: String,
    val url: String,
    val description: String,
    val language: String?,
    val languageColor: String,
    val stars: Int,
    val forks: Int,
    val currentPeriodStars: Int
) {
    fun toRepo(): Repo {
        return Repo(
            0,
            name,
            description,
            language ?: "Unknown",
            false,
            "$author/$name",
            url,
            null,
            null,
            null,
            0,
            User(
                author,
                avatar,
                null,
                "",
                "",
                "",
                0,
                0
            ),
            null,
            forks,
            null,
            null,
            null,
            stars
        )
    }
}