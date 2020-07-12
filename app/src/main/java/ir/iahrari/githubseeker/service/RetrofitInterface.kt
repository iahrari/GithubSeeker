package ir.iahrari.githubseeker.service

import ir.iahrari.githubseeker.BuildConfig
import ir.iahrari.githubseeker.service.model.AccessToken
import ir.iahrari.githubseeker.service.model.Content
import ir.iahrari.githubseeker.service.model.Repo
import ir.iahrari.githubseeker.service.model.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

//@Singleton
interface RetrofitInterface {

    companion object {
        /**
         * You should get your own api keys from github
         * then create a file named apikey.properties in your project root directory
         * and add following lines to the file:
         * CLIENT_SECRET="YOUR_CLIENT_SECRET_KEY"
         * CLIENT_ID="YOUR_CLIENT_ID"
         * For more information about what's happening here read this article:
         * https://medium.com/@geocohn/keeping-your-android-projects-secrets-secret-393b8855765d
         */
        const val clientId = BuildConfig.CLIENT_ID
        const val clientSecret = BuildConfig.CLIENT_SECRET
        const val redirectURL = "androidapp://iman-ahrari.ir:88"
        const val baseURL = "https://api.github.com/"
    }

    @GET("/user/repos")
    suspend fun getReposAsync(): Response<List<Repo>>

    @GET("/user")
    suspend fun getUserDetail(): Response<User>

    @GET("/repos/{name}/{repo}/contents")
    suspend fun getSingleRepo(
        @Path("name") name: String,
        @Path("repo") repo: String
    ): Response<List<Content>>

    @GET
    suspend fun getContents(
        @Url url: String
    ): Response<List<Content>>

    @Headers("Accept: application/json")
    @POST("https://github.com/login/oauth/access_token")
    @FormUrlEncoded
    suspend fun getAccessTokenAsync(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String
    ): Response<AccessToken>

    @Headers("Accept: application/vnd.github.3.html")
    @GET
    suspend fun getContentMarkUpView(
        @Url url: String
    ): Response<ResponseBody>

    @GET
    suspend fun getContentJson(@Url url: String): Response<Content>
}