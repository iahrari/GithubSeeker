package com.example.retrofitproject
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
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
        getUser()

    }

    private fun getUser(){
        scope.launch {
            try {
                val response = client.getUserDetail(context!!.getToken()!!)
                if (response.isSuccessful)
                    setUpUserView(response.body())
                else
                    context?.processResponseCode(response.code())

            } catch (t: Throwable){
                if (context != null)
                    Toast.makeText(context, t.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpUserView(user: User?){
        name.text = "Username: ${user?.username}"
        username.text = "Name: ${user?.name}"
        url.text = "URL: ${user?.url}"
        blog.text = "Blog: ${user?.blog}"

        if(user!!.avatar != null)
            Glide.with(this).load(user.avatar).into(avatar)
        else if(user.gravatar != null)
            Glide.with(this).load(user.gravatar).into(avatar)

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
