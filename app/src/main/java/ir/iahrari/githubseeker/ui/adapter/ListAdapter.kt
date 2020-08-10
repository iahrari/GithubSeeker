package ir.iahrari.githubseeker.ui.adapter

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import ir.iahrari.githubseeker.ui.adapter.ListAdapter.MVHolder
import androidx.recyclerview.widget.DiffUtil
import ir.iahrari.githubseeker.databinding.RecyclerItemBinding
import ir.iahrari.githubseeker.R
import ir.iahrari.githubseeker.service.model.trending.TrendingRepo
import ir.iahrari.githubseeker.ui.view.MainFragmentDirections

class ListAdapter : ListAdapter<TrendingRepo, MVHolder>(MDiffUtil()) {

    override fun onCreateViewHolder(group: ViewGroup, itemType: Int): MVHolder =
        MVHolder.from(group, R.layout.recycler_item)


    override fun onBindViewHolder(holder: MVHolder, position: Int) =
        holder.bindView(getItem(position))

    class MVHolder(private val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(data: TrendingRepo){
            binding.repo = data.toRepo()
            binding.root.setOnClickListener {
                binding.root.findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToRepoFragment(
                        data.toRepo()
                    )
                )
            }
            binding.trendStars.setOnClickListener {
                Toast.makeText(binding.root.context, "Not implemented yet!", Toast.LENGTH_LONG).show()
            }
        }

        companion object{
            fun from(group: ViewGroup, layout: Int): MVHolder =
                MVHolder(DataBindingUtil.inflate(
                    LayoutInflater.from(group.context), layout, group, false
                ))
        }
    }

    class MDiffUtil : DiffUtil.ItemCallback<TrendingRepo>() {
        override fun areItemsTheSame(p0: TrendingRepo, p1: TrendingRepo): Boolean =
            p0.author + '/' + p0.name == p1.author + '/' + p1.name

        override fun areContentsTheSame(p0: TrendingRepo, p1: TrendingRepo): Boolean =
            p0 == p1

    }
}