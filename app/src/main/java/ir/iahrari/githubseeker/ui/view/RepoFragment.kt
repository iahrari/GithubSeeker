package ir.iahrari.githubseeker.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ir.iahrari.githubseeker.databinding.FragmentRepoBinding
import ir.iahrari.githubseeker.R
import ir.iahrari.githubseeker.service.model.Content
import ir.iahrari.githubseeker.service.model.Repo
import ir.iahrari.githubseeker.service.util.setLanguageLogo
import ir.iahrari.githubseeker.ui.adapter.ContentListAdapter
import ir.iahrari.githubseeker.ui.util.MiddleDividerItemDecoration
import ir.iahrari.githubseeker.viewmodel.RepoFViewModel
import kotlinx.android.synthetic.main.fragment_repo.*

class RepoFragment : Fragment() {
    private lateinit var repo: Repo
    private lateinit var adapter: ContentListAdapter
    private lateinit var binding: FragmentRepoBinding
    private lateinit var viewModel: RepoFViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.fragment_repo, container, false)
        val args by navArgs<RepoFragmentArgs>()
        repo = args.repo
        viewModel = ViewModelProvider(this,
            RepoFViewModel.Factory(context!!, repo)
        ).get(RepoFViewModel::class.java)
        activity?.findViewById<TextView>(R.id.header_title)?.text = repo.name

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ContentListAdapter(true)
        content_recycler.addItemDecoration(
            MiddleDividerItemDecoration(
                context!!,
                MiddleDividerItemDecoration.VERTICAL,
                59
            )
        )

        content_recycler.adapter = adapter
        viewModel.contentsList.observe(this,
            Observer<List<Content>> { adapter.submitList(it as MutableList) })
        binding.repo = repo
        binding.repoLangLogo.setLanguageLogo(repo.language)
    }
}