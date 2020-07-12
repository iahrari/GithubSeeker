package ir.iahrari.githubseeker.service

import ir.iahrari.githubseeker.service.model.Content
import ir.iahrari.githubseeker.service.model.Repo
import ir.iahrari.githubseeker.service.model.User
import javax.inject.Inject

class Repository @Inject constructor(private val client: RetrofitInterface){
//    @Inject lateinit

    suspend fun getUser(
//        token: String
    ): User? {
        val response = client.getUserDetail(
//            token
        )
        if (response.isSuccessful)
            return response.body()
        else
            throw Throwable(response.code().toString(), Throwable("codeProblem"))
    }

    suspend fun getSingleRepo(
//        token: String ,
        path: String): List<Content>? {
        val name = path.split("/")
        val response = client.getSingleRepo(
//            token,
            name[0], name[1])
        if (response.isSuccessful)
            return response.body()
        else
            throw Throwable(response.code().toString(), Throwable("codeProblem"))
    }

    suspend fun getRepos(
//        accessToken: String
    ): List<Repo> {
        val response = client.getReposAsync(
//            accessToken
        )

        if (response.isSuccessful) {
            return response.body()!!
        } else
            throw Throwable(response.body().toString(), Throwable("codeProblem"))
    }

    suspend fun getContents(
//        accessToken: String,
        url: String): List<Content> {
        val response = client.getContents(
//            accessToken,
            url)
        if (response.isSuccessful)
            return response.body()!!
        else
            throw Throwable(response.body().toString(), Throwable("codeProblem"))
    }
}