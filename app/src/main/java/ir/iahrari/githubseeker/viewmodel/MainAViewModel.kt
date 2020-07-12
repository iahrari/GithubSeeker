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
import ir.iahrari.githubseeker.service.model.User
//import ir.iahrari.githubseeker.service.util.getToken
import ir.iahrari.githubseeker.service.util.processResponseCode
import kotlinx.coroutines.launch

class MainAViewModel @ViewModelInject constructor(
    @ApplicationContext context: Context,
    repository: Repository
): BaseViewModel(context, repository) {
    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User> get() = _userData

    init {
        scope.launch {
            try {
                _userData.postValue(repository.getUser(
//                    context.getToken()!!
                ))
            } catch (t: Throwable) {
                if (t.cause?.message == "codeProblem")
                    context.processResponseCode(t.message!!.toInt())
                else{
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                    Log.i("Retrofit", t.message.toString())
                }
            }
        }
    }

//    class Factory(val context: Context): ViewModelProvider.Factory{
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(MainAViewModel::class.java)) {
//                @Suppress("UNCHECKED_CAST")
//                return MainAViewModel(context) as T
//
//            }
//            throw IllegalArgumentException("Unable to construct ViewModel")
//        }
//    }
}