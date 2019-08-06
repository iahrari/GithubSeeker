package com.example.retrofitproject

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.retrofitproject.ListAdapter.MVHolder
import kotlinx.android.synthetic.main.recycler_item.view.*
import androidx.recyclerview.widget.DiffUtil

class ListAdapter : ListAdapter<Repo, MVHolder>(MDiffUtil()) {

    override fun onCreateViewHolder(group: ViewGroup, itemType: Int): MVHolder {
        return MVHolder(LayoutInflater.from(group.context).inflate(R.layout.recycler_item, group, false))
    }

    override fun onBindViewHolder(holder: MVHolder, position: Int) {

        val data = getItem(position)
        holder.view.setOnClickListener {
            holder.view.findNavController().navigate(MainFragmentDirections.actionMainFragmentToRepoFragment(data!!))
        }

        holder.view.repo_name.text = data.name
        holder.view.repo_language.text = data.language
        holder.view.repo_description.text = data.description

        if(data.private)
            holder.view.repo_name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_locked, 0, 0, 0)

        if (data.language != null)
            holder.view.repo_lang_logo.setImageResource(
                when (data.language) {
                    "Kotlin" -> R.drawable.ic_kotlin
                    "Java" -> R.drawable.ic_java
                    "Go" -> R.drawable.ic_go
                    else -> android.R.drawable.stat_notify_error
                }
            )
        else
            holder.view.repo_lang_logo.setImageResource(android.R.drawable.stat_notify_error)
    }

    class MVHolder(val view: View) : RecyclerView.ViewHolder(view)

    class MDiffUtil : DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(p0: Repo, p1: Repo): Boolean {
            return p0.id == p1.id
        }

        override fun areContentsTheSame(p0: Repo, p1: Repo): Boolean {
            return p0 == p1
        }
    }
}