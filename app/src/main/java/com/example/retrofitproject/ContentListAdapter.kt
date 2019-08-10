package com.example.retrofitproject

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitproject.databinding.ContentItemBinding

class ContentListAdapter(private val isRepo: Boolean) :
    ListAdapter<Content, ContentListAdapter.CLAHolder>(MDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CLAHolder =
        CLAHolder.from(parent, R.layout.content_item, isRepo)

    //Override submitList to sort list when submitting lists
    override fun submitList(list: MutableList<Content>?) {
        list!!.sortWith(
            Comparator { o1, o2 ->
                when {
                    o1.type == o2.type && o1.name.toLowerCase() < o2.name.toLowerCase() -> -1
                    o1.type == o2.type && o1.name.toLowerCase() > o2.name.toLowerCase() -> 1
                    o1.type == o2.type && o1.name.toLowerCase() == o2.name.toLowerCase() -> 0
                    o1.type == "file" -> 1
                    o1.type == "dir" -> -1
                    else -> 0
                }
            }
        )

        super.submitList(list)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CLAHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    class CLAHolder(private val binding: ContentItemBinding, private val isRepo: Boolean) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(data: Content) {
            binding.content = data
            val nav = if (data.type == "dir")
                bindViewsForDirectory(data)
            else
                bindViewsForFile(data)

            binding.root.setOnClickListener {
                binding.root.findNavController().navigate(nav)
            }
        }

        private fun bindViewsForDirectory(data: Content): NavDirections {
            //Open Directory
            return if (isRepo)
                RepoFragmentDirections.actionRepoFragmentToFilesFragment(data.name, data.url!!)
            else
                FilesFragmentDirections.actionFilesFragmentSelf(data.name, data.url!!)
        }

        private fun bindViewsForFile(data: Content): NavDirections {
            binding.downloadButton.setOnClickListener {
                Uri.parse(data.downloadURl)
                    .downloadFromUri(
                        binding.root.context,
                        data.name,
                        data.hUrl.split("github.com/")[1]
                    )
            }

            //Open file with github built in mark down for documents
            return if (isRepo)
                RepoFragmentDirections.actionRepoFragmentToContentFragment(data)
            else
                FilesFragmentDirections.actionFilesFragmentToContentFragment(data)

        }

        companion object {
            fun from(parent: ViewGroup, layout: Int, isRepo: Boolean): CLAHolder =
                CLAHolder(
                    DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false),
                    isRepo
                )
        }
    }

    class MDiffUtil : DiffUtil.ItemCallback<Content>() {
        override fun areItemsTheSame(p0: Content, p1: Content): Boolean {
            return p0.path == p1.path
        }

        override fun areContentsTheSame(p0: Content, p1: Content): Boolean {
            return p0 == p1
        }
    }
}