package com.example.retrofitproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_files.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FilesFragment : Fragment() {
    private lateinit var adapter: ContentListAdapter
    private lateinit var url: String
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)
    private lateinit var viewModel: FilesFViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args by navArgs<FilesFragmentArgs>()
        activity?.findViewById<TextView>(R.id.header_title)?.text = args.title
        url = args.path
        viewModel =
            ViewModelProvider(this, FilesFViewModel.Factory(context!!, url)).get(FilesFViewModel::class.java)
        return inflater.inflate(R.layout.fragment_files, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ContentListAdapter(false)
        content_recycler.addItemDecoration(
            MiddleDividerItemDecoration(
                context!!,
                MiddleDividerItemDecoration.VERTICAL,
                59
            )
        )

        viewModel.contentsList.observe(
            this,
            Observer<List<Content>> { adapter.submitList(it as MutableList) }
        )
        content_recycler.adapter = adapter
    }
}