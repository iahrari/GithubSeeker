package ir.iahrari.githubseeker.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.iahrari.githubseeker.service.TrendingRepository
import ir.iahrari.githubseeker.service.model.trending.TrendingDevelopers
import ir.iahrari.githubseeker.service.model.trending.TrendingLang
import ir.iahrari.githubseeker.service.model.trending.TrendingRepo
import ir.iahrari.githubseeker.service.model.trending.TrendingSince
import ir.iahrari.githubseeker.service.util.processResponseCode
import kotlinx.coroutines.launch

class MainFViewModel @ViewModelInject constructor(
    @ApplicationContext context: Context,
    private val repo: TrendingRepository
): BaseViewModel(context, null) {
    private val _reposList = MutableLiveData<List<TrendingRepo>>()
    val reposList: LiveData<List<TrendingRepo>> get() = _reposList

    private val _languageList = MutableLiveData<List<TrendingLang>>()
    val languageList: LiveData<List<TrendingLang>> get() = _languageList

    private val _devList = MutableLiveData<List<TrendingDevelopers>>()
    val devList: LiveData<List<TrendingDevelopers>> get() = _devList

    private var language: String? = null
    var since: TrendingSince = TrendingSince.Daily

    init {
        getLanguages()
        getRepos()
    }

    fun setLanguage(position: Int){
        language = languageList.value!![position].id
    }


    private fun getLanguages(){
        scope.launch {
            try {
                _languageList.postValue(repo.getLanguages())
            } catch (t: Throwable){
                if (t.cause?.message == "codeProblem"){
                    context.processResponseCode(t.message!!.toInt())
                    Log.i("RetrofitProblem", t.message.toString())
                } else
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getDevelopers(){
        scope.launch {
            try {
                _devList.postValue(repo.getTrendingDevelopers(language, since))
            } catch (t: Throwable){
                if (t.cause?.message == "codeProblem"){
                    context.processResponseCode(t.message!!.toInt())
                    Log.i("RetrofitProblem", t.message.toString())
                } else
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getRepos() {
        scope.launch {
            try {
                _reposList.postValue(repo.getTrendingRepos(language, since))
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