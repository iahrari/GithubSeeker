package ir.iahrari.githubseeker.ui.adapter

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.iahrari.githubseeker.databinding.ContentItemBinding
import ir.iahrari.githubseeker.R
import ir.iahrari.githubseeker.service.model.Content
import ir.iahrari.githubseeker.service.util.REQUEST_WRITE_EXTERNAL
import ir.iahrari.githubseeker.service.util.downloadFromUri
import ir.iahrari.githubseeker.ui.view.FilesListBaseFragment
import ir.iahrari.githubseeker.ui.view.FilesFragmentDirections
import ir.iahrari.githubseeker.ui.view.RepoFragmentDirections

class ContentListAdapter(private val fragment: FilesListBaseFragment, private val isRepo: Boolean) :
    ListAdapter<Content, ContentListAdapter.CLAHolder>(
        MDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CLAHolder =
        CLAHolder.from(
            fragment,
            parent,
            R.layout.content_item,
            isRepo
        )

    //Override submitList to sort list when submitting lists
    @SuppressLint("DefaultLocale")
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

        for(l in list){
            if(l.name.toLowerCase() == "readme.md"){
                fragment.onExistenceOfReadme(l)
                break
            }
        }

        super.submitList(list)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CLAHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    class CLAHolder(private val fragment: FilesListBaseFragment, private val binding: ContentItemBinding, private val isRepo: Boolean) :
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
                RepoFragmentDirections.actionRepoFragmentToFilesFragment(
                    data.name,
                    data.url!!
                )
            else
                FilesFragmentDirections.actionFilesFragmentSelf(
                    data.name,
                    data.url!!
                )
        }

        private fun bindViewsForFile(data: Content): NavDirections {
            binding.downloadButton.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
                    if(ContextCompat.checkSelfPermission(binding.root.context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                        downloadUri(data)
                    else fragment.requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_WRITE_EXTERNAL){
                        downloadUri(data)
                    }
                } else downloadUri(data)
            }

            //Open file with github built in mark down for documents
            return if (isRepo)
                RepoFragmentDirections.actionRepoFragmentToContentFragment(
                    data
                )
            else
                FilesFragmentDirections.actionFilesFragmentToContentFragment(
                    data
                )

        }

        private fun downloadUri(data: Content){
            Uri.parse(data.downloadURl)
                .downloadFromUri(
                    binding.root.context,
                    data.name,
                    data.hUrl.split("github.com/")[1]
                )
        }

        companion object {
            fun from(fragment: FilesListBaseFragment, parent: ViewGroup, layout: Int, isRepo: Boolean): CLAHolder =
                CLAHolder(
                    fragment,
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        layout,
                        parent,
                        false
                    ),
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

    interface OnExistenceOfReadme{
        fun onExistenceOfReadme(content: Content)
    }
}