package ir.iahrari.githubseeker.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ir.iahrari.githubseeker.databinding.FragmentRepoBinding
import ir.iahrari.githubseeker.R
import ir.iahrari.githubseeker.service.model.Repo
import ir.iahrari.githubseeker.service.util.setImageWithURL
import ir.iahrari.githubseeker.service.util.setLanguageLogo
import ir.iahrari.githubseeker.ui.adapter.ContentListAdapter
import ir.iahrari.githubseeker.ui.util.MiddleDividerItemDecoration
import ir.iahrari.githubseeker.viewmodel.RepoFViewModel
import kotlinx.android.synthetic.main.fragment_repo.*

@AndroidEntryPoint
class RepoFragment : BasePermissionFragment() {
    private lateinit var repo: Repo
    private lateinit var adapter: ContentListAdapter
    private lateinit var binding: FragmentRepoBinding
    private val viewModel by viewModels<RepoFViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.fragment_repo, container, false)
        val args by navArgs<RepoFragmentArgs>()
        repo = args.repo
        Log.i("description", repo.description?: "")
        viewModel.setRepo(repo)
        activity?.findViewById<TextView>(R.id.header_title)?.text = repo.name

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ContentListAdapter(this, true)
        content_recycler.addItemDecoration(
            MiddleDividerItemDecoration(
                requireContext(),
                MiddleDividerItemDecoration.VERTICAL,
                59
            )
        )

        content_recycler.adapter = adapter
        viewModel.contentsList.observe(viewLifecycleOwner,
            Observer { adapter.submitList(it as MutableList) })
        viewModel.gRepo.observe(viewLifecycleOwner,
            Observer {
                repo = it
                binding.repo = repo
            })

        binding.repo = repo
        binding.ownerAvatarLogo.setImageWithURL(repo.owner!!.avatar)
    }
}
