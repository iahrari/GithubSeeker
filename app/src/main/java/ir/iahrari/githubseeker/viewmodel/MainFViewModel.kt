package ir.iahrari.githubseeker.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.iahrari.githubseeker.service.Repository
import ir.iahrari.githubseeker.service.model.Repo
import ir.iahrari.githubseeker.service.util.processResponseCode
import kotlinx.coroutines.launch

class MainFViewModel @ViewModelInject constructor(
    @ApplicationContext context: Context,
    repository: Repository
): BaseViewModel(context, repository) {
    private val _reposList = MutableLiveData<List<Repo>>()
    val reposList: LiveData<List<Repo>> get() = _reposList
    init {
        getRepos()

    }

    private fun getRepos() {
        scope.launch {
            try {
                _reposList.postValue(repository.getRepos())
            } catch (t: Throwable) {

                if (t.cause?.message == "codeProblem"){
                    context.processResponseCode(t.message!!.toInt())
                    Log.i("RetrofitProblem", t.message.toString())
                } else
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}