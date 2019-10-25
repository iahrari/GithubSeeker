package ir.iahrari.githubseeker.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.iahrari.githubseeker.service.model.Repo
import ir.iahrari.githubseeker.service.util.getToken
import ir.iahrari.githubseeker.service.util.processResponseCode
import kotlinx.coroutines.launch

class MainFViewModel(context: Context): BaseViewModel(context) {
    private val _reposList = MutableLiveData<List<Repo>>()
    val reposList: LiveData<List<Repo>> get() = _reposList

    init {
        getRepos()
    }

    private fun getRepos() {
        scope.launch {
            try {
                _reposList.postValue(repository.getRepos(context.getToken()!!))
            } catch (t: Throwable) {

                if (t.cause?.message == "codeProblem")
                    context.processResponseCode(t.message!!.toInt())
                else
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    class Factory(val context: Context): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainFViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainFViewModel(context) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }
}