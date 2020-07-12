package ir.iahrari.githubseeker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import ir.iahrari.githubseeker.service.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseViewModel (
    protected val context: Context,
    protected val repository: Repository
): ViewModel() {
    private val job = Job()
    protected val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}