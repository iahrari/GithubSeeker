package ir.iahrari.githubseeker.service

import ir.iahrari.githubseeker.service.model.trending.TrendingDevelopers
import ir.iahrari.githubseeker.service.model.trending.TrendingLang
import ir.iahrari.githubseeker.service.model.trending.TrendingRepo
import ir.iahrari.githubseeker.service.model.trending.TrendingSince
import javax.inject.Inject

class TrendingRepository @Inject constructor(private val client: TrendingServiceInterface) {
    suspend fun getTrendingRepos(
        language: String?,
        since: TrendingSince?
    ): List<TrendingRepo>? {
        val response = client.getTrendingRepos(language, since)
        if (response.isSuccessful)
            return response.body()
        else throw Throwable(response.code().toString(), Throwable("codeProblem"))
    }

    suspend fun getLanguages(): List<TrendingLang>? {
        val response = client.getLanguages()
        if (response.isSuccessful)
            return response.body()
        else throw Throwable(response.code().toString(), Throwable("codeProblem"))
    }

    suspend fun getTrendingDevelopers(
        language: String?,
        since: TrendingSince?
    ): List<TrendingDevelopers>? {
        val response = client.getTrendingDevelopers(language, since)
        if (response.isSuccessful)
            return response.body()
        else throw Throwable(response.code().toString(), Throwable("codeProblem"))
    }
}