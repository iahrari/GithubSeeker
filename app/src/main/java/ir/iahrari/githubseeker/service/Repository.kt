package ir.iahrari.githubseeker.service

import ir.iahrari.githubseeker.service.model.Content
import ir.iahrari.githubseeker.service.model.Repo
import ir.iahrari.githubseeker.service.model.User
import javax.inject.Inject

class Repository @Inject constructor(private val client: RetrofitInterface){

    suspend fun getUser(): User? {
        val response = client.getUserDetail()
        if (response.isSuccessful)
            return response.body()
        else
            throw Throwable(response.code().toString(), Throwable("codeProblem"))
    }

    suspend fun getSingleRepo(path: String): List<Content>? {
        val name = path.split("/")
        val response = client.getSingleRepo(name[0], name[1])
        if (response.isSuccessful)
            return response.body()
        else
            throw Throwable(response.code().toString(), Throwable("codeProblem"))
    }

    suspend fun getRepos(): List<Repo> {
        val response = client.getReposAsync()

        if (response.isSuccessful) {
            return response.body()!!
        } else
            throw Throwable(response.body().toString(), Throwable("codeProblem"))
    }

    suspend fun getContents(
        url: String): List<Content> {
        val response = client.getContents(url)
        if (response.isSuccessful)
            return response.body()!!
        else
            throw Throwable(response.body().toString(), Throwable("codeProblem"))
    }
}