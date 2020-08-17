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

    suspend fun getSingleRepoContents(path: String): List<Content>? {
        val p = path.split("/")
        val response = client.getSingleRepoContents(p[0], p[1])
        if (response.isSuccessful)
            return response.body()
        else
            throw Throwable(response.code().toString(), Throwable("codeProblem"))
    }

    suspend fun getRepo(path: String): Repo?{
        val p = path.split("/")
        val response = client.getRepo(p[0], p[1])
        if(response.isSuccessful)
            return response.body()
        else throw Throwable(response.code().toString(), Throwable("codeProblem"))
    }

    suspend fun getRepos(): List<Repo> {
        val response = client.getReposAsync()

        if (response.isSuccessful) {
            return response.body()!!
        } else
            throw Throwable(response.body().toString(), Throwable("codeProblem"))
    }

    suspend fun getContents(url: String): List<Content> {
        val response = client.getContents(url)
        if (response.isSuccessful)
            return response.body()!!
        else
            throw Throwable(response.body().toString(), Throwable("codeProblem"))
    }

    suspend fun getReadme(url: String): String{
        val response = client.getContentMarkUpView(url)
        if (response.isSuccessful)
            return response.body()!!.string()

        else
            throw Throwable(response.body().toString(), Throwable("codeProblem"))
    }
}