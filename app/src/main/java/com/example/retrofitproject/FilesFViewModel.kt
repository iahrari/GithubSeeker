package com.example.retrofitproject

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.launch

class FilesFViewModel(context: Context, private val url: String): BaseViewModel(context) {

    private val _contentsList = MutableLiveData<List<Content>>()
    val contentsList: LiveData<List<Content>> get() = _contentsList

    init {
        scope.launch {
            try {
                _contentsList.postValue(repository.getContents(context.getToken()!!, url))
            } catch (t: Throwable) {
                if (t.cause?.message == "codeProblem")
                    context.processResponseCode(t.message!!.toInt())
                else
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    class Factory(val context: Context, val url: String): ViewModelProvider.Factory{
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FilesFViewModel::class.java))
                return FilesFViewModel(context, url) as T
            else
                throw IllegalArgumentException("Unable to construct viewmodel")
        }

    }
}