package ir.iahrari.githubseeker.ui.view

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ir.iahrari.githubseeker.service.util.REQUEST_WRITE_EXTERNAL
import javax.inject.Inject


open class BasePermissionFragment: Fragment() {
    private var containsOperation = false
    private lateinit var operation: () -> Unit
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
}