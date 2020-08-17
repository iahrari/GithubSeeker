package ir.iahrari.githubseeker.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ir.iahrari.githubseeker.service.Repository
import ir.iahrari.githubseeker.service.RetrofitInterface
import ir.iahrari.githubseeker.service.model.Content
import ir.iahrari.githubseeker.service.util.processResponseCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface RetrieveReadme {
    fun retrieveReadme(
        scope: CoroutineScope,
        content: Content,
        repo: Repository
    ): LiveData<String?> {
        val data = MutableLiveData<String?>("")
        scope.launch {
            try {
                data.postValue(repo.getReadme(content.url!!))
            }catch (t: Throwable){
                data.postValue(null)
            }
        }

        return data
    }

    fun getReadme(content: Content): LiveData<String?>
}