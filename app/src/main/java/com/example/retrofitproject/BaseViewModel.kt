package com.example.retrofitproject

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseViewModel(protected val context: Context): ViewModel() {
    private val job = Job()
    protected val scope = CoroutineScope(Dispatchers.Main + job)
    protected val repository = Repository()

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}