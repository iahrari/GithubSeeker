package ir.iahrari.githubseeker.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ir.iahrari.githubseeker.viewmodel.MainAViewModel
import ir.iahrari.githubseeker.R
import ir.iahrari.githubseeker.databinding.ActivityMainBinding
import ir.iahrari.githubseeker.databinding.NavigationHeaderBinding
import ir.iahrari.githubseeker.service.model.User

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var headerBinding: NavigationHeaderBinding
    private val viewModel by viewModels<MainAViewModel> ()
    private var drawerListenerItem: OnDrawerMenuItemClicked? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        setupNavigation()
        supportActionBar!!.setDisplayShowTitleEnabled(false)
//        viewModel = ViewModelProvider(this,
//            MainAViewModel.Factory(this)
//        ).get(MainAViewModel::class.java)
        setObservers()
    }

    private fun setObservers() {
        viewModel.userData.observe(this, Observer<User> { t -> setUpUserView(t) })
    }

    override fun onSupportNavigateUp(): Boolean =
        navigateUp(
            findNavController(R.id.nav_host_fragment),
            binding.drawerLayout
        )

    private fun setupNavigation() {
        headerBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
            R.layout.navigation_header, binding.navigation, false)

        val navController = findNavController(R.id.nav_host_fragment)

        setSupportActionBar(binding.toolbar)

        setupActionBarWithNavController(navController, binding.drawerLayout)

        binding.navigation.setupWithNavController(navController)

        binding.navigation.addHeaderView(headerBinding.root)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.label) {
                "fragment_main" -> binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                else -> binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

        binding.navigation.setNavigationItemSelectedListener {
            drawerListenerItem?.onDrawerMenuItemClicked(it.itemId)
            true
        }
    }

    private fun setUpUserView(user: User?){
        headerBinding.user = user

        if(user!!.avatar != null)
            Glide.with(this).load(user.avatar).into(headerBinding.avatar)
        else if(user.gravatar != null)
            Glide.with(this).load(user.gravatar).into(headerBinding.avatar)
    }

    fun setDrawerListener(listener: OnDrawerMenuItemClicked){
        drawerListenerItem = listener
    }

    interface OnDrawerMenuItemClicked {
        fun onDrawerMenuItemClicked(id: Int)
    }
}