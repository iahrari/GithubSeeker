package com.example.retrofitproject

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.retrofitproject.ListAdapter.MVHolder
import kotlinx.android.synthetic.main.recycler_item.view.*
import androidx.recyclerview.widget.DiffUtil
import com.example.retrofitproject.databinding.RecyclerItemBinding

class ListAdapter : ListAdapter<Repo, MVHolder>(MDiffUtil()) {

    override fun onCreateViewHolder(group: ViewGroup, itemType: Int): MVHolder =
        MVHolder.from(group, R.layout.recycler_item)


    override fun onBindViewHolder(holder: MVHolder, position: Int) =
        holder.bindView(getItem(position))

    class MVHolder(private val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(data: Repo){
            binding.repo = data
            binding.root.setOnClickListener {
                binding.root.findNavController().navigate(MainFragmentDirections.actionMainFragmentToRepoFragment(data))
            }

            binding.repoLangLogo.setLanguageLogo(data.language)
        }

        companion object{
            fun from(group: ViewGroup, layout: Int): MVHolder =
                MVHolder(DataBindingUtil.inflate(
                    LayoutInflater.from(group.context), layout, group, false
                ))
        }
    }

    class MDiffUtil : DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(p0: Repo, p1: Repo): Boolean =
            p0.id == p1.id

        override fun areContentsTheSame(p0: Repo, p1: Repo): Boolean =
            p0 == p1

    }
}