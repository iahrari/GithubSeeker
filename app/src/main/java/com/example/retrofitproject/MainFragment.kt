package com.example.retrofitproject
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)
    private lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<TextView>(R.id.header_title)?.text = context?.getString(R.string.github_seeker)
        recycler.addItemDecoration(MiddleDividerItemDecoration(context!!, MiddleDividerItemDecoration.ALL, 0))

        adapter = ListAdapter()
        recycler.adapter = adapter
        getRepos(context!!.getToken()!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun getRepos(accessToken: String) {
        scope.launch {
            try {
                val response = client.getReposAsync(accessToken)

                if (response.isSuccessful) {
                    adapter.submitList(response.body())
                } else
                    context!!.processResponseCode(response.code())

            } catch (t: Throwable) {
                Log.i("logRepoProblemCatch", t.message)
            }
        }
    }
}