package com.example.retrofitproject

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.content_item.view.*

class ContentListAdapter(private val isRepo: Boolean): ListAdapter<Content, ContentListAdapter.CLAHolder>(MDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CLAHolder =
        CLAHolder.from(parent, R.layout.content_item, isRepo)


    //Override submitLists to sort list when submitting lists
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

    class CLAHolder(private val view: View, private val isRepo: Boolean) : RecyclerView.ViewHolder(view){

        fun bindView(data: Content){
             if(data.type == "dir")
                bindViewsForDirectory(data)
             else
                 bindViewsForFile(data)

            view.content_name.text = data.name
            view.content_size.text = prepareFileSize(data.size)
        }

        private fun bindViewsForDirectory(data: Content){
                //Hide file's attributes
                view.content_size.visibility = View.GONE
                view.content_type_icon.setImageResource(R.drawable.ic_folder)
                view.download_button.visibility = View.GONE

                //Open Directory
                view.setOnClickListener {
                    val nav =
                        if (isRepo)
                            RepoFragmentDirections.actionRepoFragmentToFilesFragment(data.name, data.url!!)
                        else
                            FilesFragmentDirections.actionFilesFragmentSelf(data.name, data.url!!)

                    view.findNavController().navigate(nav)
                }
        }

        private fun bindViewsForFile(data: Content){
            view.content_size.visibility = View.VISIBLE
            view.content_type_icon.setImageResource(R.drawable.ic_file)
            view.download_button.visibility = View.VISIBLE
            view.download_button.setOnClickListener {
                Uri.parse(data.downloadURl)
                    .downloadFromUri(
                        view.context,
                        data.name,
                        data.hUrl.split("github.com/")[1]
                    )
            }

            //Open file with github built in mark down for documents
            view.setOnClickListener {
                val nav =
                    if (isRepo)
                        RepoFragmentDirections.actionRepoFragmentToContentFragment(data)
                    else
                        FilesFragmentDirections.actionFilesFragmentToContentFragment(data)

                view.findNavController().navigate(nav)
            }
        }

        companion object {
            fun from(parent: ViewGroup, layout: Int, isRepo: Boolean): CLAHolder =
                CLAHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false), isRepo)
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