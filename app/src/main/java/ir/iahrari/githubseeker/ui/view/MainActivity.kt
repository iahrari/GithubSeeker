package ir.iahrari.githubseeker.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import ir.iahrari.githubseeker.viewmodel.MainAViewModel
import ir.iahrari.githubseeker.R
import ir.iahrari.githubseeker.databinding.ActivityMainBinding
import ir.iahrari.githubseeker.databinding.NavigationHeaderBinding
import ir.iahrari.githubseeker.service.model.User
import ir.iahrari.githubseeker.service.util.setImageWithURL

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var headerBinding: NavigationHeaderBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<MainAViewModel>()
    private var drawerListenerItem: OnDrawerMenuItemClicked? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        setupNavigation()
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        setObservers()
    }

    private fun setObservers() {
        viewModel.userData.observe(this, Observer<User> { t -> setUpUserView(t) })
    }

    override fun onSupportNavigateUp(): Boolean {
        return when (navController.currentDestination?.label) {
            "fragment_main" -> {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                } else navigateUp(
                    navController,
                    binding.drawerLayout
                ) || super.onSupportNavigateUp()
            } else -> navigateUp(
                navController,
                binding.drawerLayout
            ) || super.onSupportNavigateUp()
        }
    }

    private fun setupNavigation() {
        headerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.navigation_header, binding.navigation, false
        )
        navController = findNavController(R.id.nav_host_fragment)

        setSupportActionBar(binding.toolbar)
        val appBC =
            AppBarConfiguration.Builder(navController.graph).setOpenableLayout(binding.drawerLayout)
                .setFallbackOnNavigateUpListener {
                    Log.i(
                        "fallback",
                        binding.drawerLayout.isDrawerOpen(GravityCompat.START).toString()
                    )
                    !binding.drawerLayout.isDrawerOpen(GravityCompat.START)
                }.build()
        NavigationUI.setupActionBarWithNavController(this, navController, appBC)

        binding.navigation.setupWithNavController(navController)

        binding.navigation.addHeaderView(headerBinding.root)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.label) {
                "fragment_main" -> binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                else -> binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {}
            override fun onDrawerClosed(drawerView: View) {}
            override fun onDrawerOpened(drawerView: View) {}

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                (binding.toolbar.navigationIcon as DrawerArrowDrawable).progress = slideOffset
            }


        })

        binding.navigation.setNavigationItemSelectedListener {
            drawerListenerItem?.onDrawerMenuItemClicked(it.itemId)
            true
        }
    }

    private fun setUpUserView(user: User?) {
        headerBinding.user = user
        headerBinding.avatar.setImageWithURL(user?.avatar)
//        if (user!!.avatar != null)
//            Glide.with(this).load(user.avatar).circleCrop().into(headerBinding.avatar)

//        else if (user.gravatar != null)
//            Glide.with(this).load(user.gravatar).into(headerBinding.avatar)
    }

    fun setDrawerListener(listener: OnDrawerMenuItemClicked) {
        drawerListenerItem = listener
    }

    interface OnDrawerMenuItemClicked {
        fun onDrawerMenuItemClicked(id: Int)
    }
}