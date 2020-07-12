package ir.iahrari.githubseeker.ui.view
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ir.iahrari.githubseeker.R
import ir.iahrari.githubseeker.service.model.Repo
import ir.iahrari.githubseeker.ui.adapter.ListAdapter
import ir.iahrari.githubseeker.ui.util.MiddleDividerItemDecoration
import ir.iahrari.githubseeker.viewmodel.MainFViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), MainActivity.OnDrawerMenuItemClicked {
    override fun onDrawerMenuItemClicked(id: Int) {
        when(id){
            R.id.latest_activities ->
                Toast.makeText(context, "Latest Activities", Toast.LENGTH_LONG).show()
        }
    }

    private lateinit var adapter: ListAdapter
    private lateinit var viewModel: MainFViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).setDrawerListener(this)
        viewModel = ViewModelProvider(this,
            MainFViewModel.Factory(requireContext())
        ).get(MainFViewModel::class.java)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<TextView>(R.id.header_title)?.text = context?.getString(
            R.string.github_seeker
        )
        recycler.addItemDecoration(
            MiddleDividerItemDecoration(
                requireContext(),
                MiddleDividerItemDecoration.ALL,
                0
            )
        )

        adapter = ListAdapter()
        recycler.adapter = adapter

        viewModel.reposList.observe(viewLifecycleOwner, Observer<List<Repo>> { adapter.submitList(it) })
    }
}