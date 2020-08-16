package ir.iahrari.githubseeker.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ir.iahrari.githubseeker.R
import ir.iahrari.githubseeker.databinding.DevelopersItemsBinding
import ir.iahrari.githubseeker.service.model.trending.TrendingDevelopers
import ir.iahrari.githubseeker.service.util.setImageWithURL

class DevelopersAdapter: androidx.recyclerview.widget.ListAdapter<TrendingDevelopers, DevelopersAdapter.DVHolder>(
    MDiffUtil()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DVHolder =
        DVHolder.from(parent, R.layout.developers_items)

    override fun onBindViewHolder(holder: DVHolder, position: Int) =
        holder.bindView(getItem(position))

    class DVHolder(private val binding: DevelopersItemsBinding): RecyclerView.ViewHolder(binding.root){
        fun bindView(data: TrendingDevelopers){
            binding.apply {
                dev = data
                devAvatar.setImageWithURL(data.avatar)
                root.setOnClickListener {
                    devUsername.performClick()
                }

                devFollow.setOnClickListener {
                    Toast.makeText(root.context, "Not implemented yet!", Toast.LENGTH_LONG).show()
                }
            }
        }

        companion object{
            fun from(group: ViewGroup, layout: Int): DVHolder =
                DVHolder(DataBindingUtil.inflate(
                    LayoutInflater.from(group.context), layout, group, false
                ))
        }
    }
    class MDiffUtil : DiffUtil.ItemCallback<TrendingDevelopers>() {
        override fun areItemsTheSame(p0: TrendingDevelopers, p1: TrendingDevelopers): Boolean =
            p0.username == p1.username

        override fun areContentsTheSame(p0: TrendingDevelopers, p1: TrendingDevelopers): Boolean =
            p0 == p1

    }
}