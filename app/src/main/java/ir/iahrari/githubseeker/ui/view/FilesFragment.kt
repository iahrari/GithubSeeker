package ir.iahrari.githubseeker.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ir.iahrari.githubseeker.R
import ir.iahrari.githubseeker.service.model.Content
import ir.iahrari.githubseeker.ui.adapter.ContentListAdapter
import ir.iahrari.githubseeker.ui.util.MiddleDividerItemDecoration
import ir.iahrari.githubseeker.viewmodel.FilesFViewModel
import kotlinx.android.synthetic.main.fragment_files.*

@AndroidEntryPoint
class FilesFragment : BasePermissionFragment() {
    private lateinit var adapter: ContentListAdapter
    private lateinit var url: String
    private val viewModel: FilesFViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args by navArgs<FilesFragmentArgs>()
        activity?.findViewById<TextView>(R.id.header_title)?.text = args.title
        url = args.path
        viewModel.setUrl(url)
        return inflater.inflate(R.layout.fragment_files, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ContentListAdapter(this, false)
        content_recycler.addItemDecoration(
            MiddleDividerItemDecoration(
                requireContext(),
                MiddleDividerItemDecoration.VERTICAL,
                59
            )
        )

        viewModel.contentsList.observe(
            viewLifecycleOwner,
            Observer<List<Content>> { adapter.submitList(it as MutableList) }
        )
        content_recycler.adapter = adapter
    }
}