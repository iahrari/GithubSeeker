package com.example.retrofitproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_repo.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RepoFragment : Fragment() {
    private lateinit var repo: Repo
    private lateinit var adapter: ContentListAdapter
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args by navArgs<RepoFragmentArgs>()
        repo = args.repo
        activity?.findViewById<TextView>(R.id.header_title)?.text = repo.name

        return inflater.inflate(R.layout.fragment_repo, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ContentListAdapter(true)
        scope.launch {
            content_recycler.addItemDecoration(MiddleDividerItemDecoration(context!!, MiddleDividerItemDecoration.VERTICAL, 59))
            content_recycler.adapter = adapter
            getContents()

            repo_name.text = repo.name
            repo_language.text = repo.language
            repo_description.text = repo.description

            if (repo.private)
                repo_name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_locked, 0, 0, 0)

            if (repo.language != null)
                repo_lang_logo.setImageResource(
                    when (repo.language) {
                        "Kotlin" -> R.drawable.ic_kotlin
                        "Java" -> R.drawable.ic_java
                        "Go" -> R.drawable.ic_go
                        else -> R.drawable.ic_github
                    }
                )
            else
                repo_lang_logo.setImageResource(android.R.drawable.stat_notify_error)

            repo_owner.text = "Owner: ${repo.owner.username}"
            repo_html_url.text = "URL: ${repo.hURL}"
            repo_clone_url.text = "Clone URL: ${repo.cloneURL}"
            repo_git_url.text = "Git URL: ${repo.gitURl}"
            repo_ssh_url.text = "SSH URL: ${repo.sshURL}"
            repo_size.text = "Contains: ${repo.size} files"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun getContents(){
        scope.launch {
            try {
                val name = repo.path!!.split("/")
                val response = client.getSingleRepo(context!!.getToken()!!, name[0], name[1])
                if (response.isSuccessful)
                    adapter.submitList(response.body() as MutableList)

                else
                    context!!.processResponseCode(response.code())
            } catch (t: Throwable){
                if (context != null)
                    Toast.makeText(context, t.message + "m", Toast.LENGTH_LONG).show()
            }
        }
    }
}
