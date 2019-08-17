package com.example.retrofitproject

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.launch

class RepoFViewModel(context: Context, val repo: Repo) : BaseViewModel(context) {
    private val _contentsList = MutableLiveData<List<Content>>()
    val contentsList: LiveData<List<Content>> get() = _contentsList

    init {
        scope.launch {
            try {
                _contentsList.postValue(repository.getSingleRepo(context.getToken()!!, repo.path!!))
            } catch (t: Throwable) {
                if (t.cause?.message == "codeProblem")
                    context.processResponseCode(t.message!!.toInt())
                else
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    class Factory(val context: Context, val repo: Repo) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RepoFViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RepoFViewModel(context, repo) as T

            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}