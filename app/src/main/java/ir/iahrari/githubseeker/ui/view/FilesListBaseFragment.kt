package ir.iahrari.githubseeker.ui.view

import android.Manifest
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import ir.iahrari.githubseeker.R
import ir.iahrari.githubseeker.databinding.ReadmeLayoutBinding
import ir.iahrari.githubseeker.service.model.Content
import ir.iahrari.githubseeker.service.util.REQUEST_WRITE_EXTERNAL
import ir.iahrari.githubseeker.ui.adapter.ContentListAdapter
import ir.iahrari.githubseeker.viewmodel.RetrieveReadme

open class FilesListBaseFragment: Fragment(), ContentListAdapter.OnExistenceOfReadme {
    private var containsOperation = false
    private lateinit var operation: () -> Unit
    protected var container: ViewGroup? = null
    protected var readmeViewModel: RetrieveReadme? = null
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_WRITE_EXTERNAL){
            if(permissions.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                if(grantResults[permissions.indexOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)] != PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(requireContext(), "Unable to save file because of the lack of permission", Toast.LENGTH_LONG).show()
                else {
                    if(containsOperation){
                        operation()
                        containsOperation = false
                    }
                }
            }
        }
    }

    fun requestPermissions(permissions: Array<out String>, requestCode: Int, operation: () -> Unit){
        containsOperation = true
        this.operation = operation
        requestPermissions(permissions, requestCode)
    }

    private fun setReadme(isContainsReadme: Boolean, content: String?){
        if (container == null || !isContainsReadme) {
            (activity as MainActivity).setBottomSheetVisibility(View.GONE)
            return
        }
        val readmeBinding: ReadmeLayoutBinding =
            DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.readme_layout, container,false)
        (activity as MainActivity).apply {
            setBottomSheetVisibility(View.VISIBLE)
            setSheetContent(readmeBinding.root)
            setSheetTitle(R.string.readme)
            setSheetTitleDrawable(R.drawable.ic_readme)
        }

        readmeBinding.webView.loadDataWithBaseURL(
            null,
            content!!,
            "text/html; charset=utf-8",
            "UTF-8",
            null
        )
    }

    override fun onExistenceOfReadme(content: Content) {
        if (readmeViewModel != null)
            readmeViewModel!!.getReadme(content).observe(viewLifecycleOwner){
                setReadme(it != null, it)
            }
    }
}