package ir.iahrari.githubseeker.service

import ir.iahrari.githubseeker.service.model.trending.TrendingDevelopers
import ir.iahrari.githubseeker.service.model.trending.TrendingLang
import ir.iahrari.githubseeker.service.model.trending.TrendingRepo
import ir.iahrari.githubseeker.service.model.trending.TrendingSince
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingServiceInterface {
    companion object{
        const val BASE_URL = "https://ghapi.huchen.dev/"
    }

    @GET("/repositories")
    suspend fun getTrendingRepos(
        @Query("language") language: String?,
        @Query("since") since: TrendingSince?
    ): Response<List<TrendingRepo>>

    @GET("/developers")
    suspend fun getTrendingDevelopers(
        @Query("language") language: String?,
        @Query("since") since: TrendingSince?
    ): Response<List<TrendingDevelopers>>

    @GET("/languages")
    suspend fun getLanguages(): Response<List<TrendingLang>>
}