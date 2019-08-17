package com.example.retrofitproject
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private lateinit var adapter: ListAdapter
    private lateinit var viewModel: MainFViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, MainFViewModel.Factory(context!!)).get(MainFViewModel::class.java)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<TextView>(R.id.header_title)?.text = context?.getString(R.string.github_seeker)
        recycler.addItemDecoration(MiddleDividerItemDecoration(context!!, MiddleDividerItemDecoration.ALL, 0))

        adapter = ListAdapter()
        recycler.adapter = adapter

        viewModel.reposList.observe(this, Observer<List<Repo>> { adapter.submitList(it) })
    }
}