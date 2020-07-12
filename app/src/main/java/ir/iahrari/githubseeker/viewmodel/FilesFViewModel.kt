package ir.iahrari.githubseeker.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.iahrari.githubseeker.service.Repository
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
import ir.iahrari.githubseeker.service.model.Content
//import ir.iahrari.githubseeker.service.util.getToken
import ir.iahrari.githubseeker.service.util.processResponseCode
import kotlinx.coroutines.launch
//import javax.inject.Inject

class FilesFViewModel @ViewModelInject constructor(
    @ApplicationContext context: Context,
    repository: Repository
): BaseViewModel(context, repository) {

    private val _contentsList = MutableLiveData<List<Content>>()
    val contentsList: LiveData<List<Content>> get() = _contentsList

    fun setUrl(url: String) {
        scope.launch {
            try {
                _contentsList.postValue(repository.getContents(
//                    context.getToken()!!,
                    url))
            } catch (t: Throwable) {
                if (t.cause?.message == "codeProblem")
                    context.processResponseCode(t.message!!.toInt())
                else
                    Log.i("Error Message", t.localizedMessage!!)
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        }
    }

//    class Factory(val context: Context, val url: String): ViewModelProvider.Factory{
//        @Suppress("UNCHECKED_CAST")
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(FilesFViewModel::class.java))
//                return FilesFViewModel(context, url) as T
//            else
//                throw IllegalArgumentException("Unable to construct viewmodel")
//        }
//
//    }
}