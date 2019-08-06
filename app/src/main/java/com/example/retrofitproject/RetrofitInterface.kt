package com.example.retrofitproject

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

val client: RetrofitInterface = Retrofit.Builder()
    .baseUrl(RetrofitInterface.baseURL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(OkHttpClient.Builder().build()).build()
    .create(RetrofitInterface::class.java)

interface RetrofitInterface {

    companion object{
        const val clientId = "77d1f4e38421e86afda2"
        const val clientSecret = "eb93f0e3880dcc2ac9674b36265e4a7dc2ac3df0"
        const val redirectURL = "androidapp://iman-ahrari.ir:88"
        const val baseURL = "https://api.github.com/"
    }

    @GET("/user/repos")
    suspend fun getReposAsync(@Header("Authorization") auth: String): Response<List<Repo>>

    @GET("/user")
    suspend fun getUserDetail(@Header("Authorization") auth: String): Response<User>

    @GET("/repos/{name}/{repo}/contents")
    suspend fun getSingleRepo(
        @Header("Authorization") auth: String,
        @Path("name") name: String,
        @Path("repo") repo: String
    ): Response<List<Content>>

    @GET
    suspend fun getContents(@Header("Authorization") auth: String, @Url url: String): Response<List<Content>>

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
    suspend fun getContentMarkUpView(@Header("Authorization") auth: String, @Url url: String): Response<ResponseBody>

    @GET
    suspend fun getContentJson(@Header("Authorization") auth: String, @Url url: String): Response<Content>

}