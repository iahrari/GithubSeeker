package ir.iahrari.githubseeker.ui.view
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import dagger.hilt.android.AndroidEntryPoint
import ir.iahrari.githubseeker.R
import ir.iahrari.githubseeker.databinding.TrendsFilterLayoutBinding
import ir.iahrari.githubseeker.service.model.trending.TrendingLang
import ir.iahrari.githubseeker.service.model.trending.TrendingSince
import ir.iahrari.githubseeker.ui.adapter.DevelopersAdapter
import ir.iahrari.githubseeker.ui.adapter.ListAdapter
import ir.iahrari.githubseeker.viewmodel.MainFViewModel
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment(), MainActivity.OnDrawerMenuItemClicked {
    private lateinit var repoAdapter: ListAdapter
    private lateinit var devAdapter: DevelopersAdapter
    private lateinit var filterBinding: TrendsFilterLayoutBinding
    private lateinit var langAdapter: ArrayAdapter<TrendingLang>
    private val viewModel: MainFViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_main, container, false)
        filterBinding = DataBindingUtil.inflate(inflater, R.layout.trends_filter_layout, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActivity()
        initFilterSheet()
        setObservers()
        setRecycler()
    }

    private fun setRecycler(){
        devAdapter = DevelopersAdapter()
        repoAdapter = ListAdapter()
        recycler.adapter = repoAdapter
    }

    private fun setObservers(){
        viewModel.reposList.observe(viewLifecycleOwner){ repoAdapter.submitList(it) }
        viewModel.devList.observe(viewLifecycleOwner){
            devAdapter.submitList(it)
        }
        viewModel.languageList.observe(viewLifecycleOwner){
            langAdapter.apply {
                clear()
                addAll(it)
                notifyDataSetChanged()
            }
        }
    }

    private fun setupActivity(){
        (activity as MainActivity).apply {
            setDrawerListener(this@MainFragment)
            setSheetContent(filterBinding.root)
            setSheetTitle(R.string.filter)
            setSheetTitleDrawable(R.drawable.ic_filter)
            findViewById<TextView>(R.id.header_title)?.text = context?.getString(
                R.string.github_seeker
            )
        }
    }

    private fun initFilterSheet(){
        filterBinding.apply {
            trendsTypeSpinner.adapter =
                ArrayAdapter<String>(
                    requireContext(),
                    R.layout.spinner_text,
                    R.id.spinner_text,
                    resources.getStringArray(R.array.trends_values)
                )

            trendsSinceSpinner.adapter =
                ArrayAdapter<String>(
                    requireContext(),
                    R.layout.spinner_text,
                    R.id.spinner_text,
                    resources.getStringArray(R.array.since_values)
                )
            langAdapter =
                ArrayAdapter(
                    requireContext(),
                    R.layout.spinner_text,
                    R.id.spinner_text,
                    mutableListOf()
                )

            trendsLanguageSpinner.adapter = langAdapter

            trendsFilterDone.setOnClickListener {
                viewModel.apply {
                    setLanguage(trendsLanguageSpinner.selectedItemPosition)
                    since = TrendingSince.values()[trendsSinceSpinner.selectedItemPosition]
                    if (trendsTypeSpinner.selectedItemPosition == 0){
                        getRepos()
                        recycler.adapter = repoAdapter
                    } else {
                        retrieveDevelopers()
                        recycler.adapter = devAdapter
                    }
                    (activity as MainActivity).closeSheet()
                }
            }
        }
    }

    override fun onDrawerMenuItemClicked(id: Int) {
        when(id){
            R.id.latest_activities ->
                Toast.makeText(context, "Latest Activities", Toast.LENGTH_LONG).show()
        }
    }
}