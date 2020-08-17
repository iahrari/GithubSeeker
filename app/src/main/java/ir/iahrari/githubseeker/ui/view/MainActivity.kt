package ir.iahrari.githubseeker.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.core.content.ContextCompat
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
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
    private lateinit var bottomSheet: BottomSheetBehavior<View>
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
            }
            else -> navigateUp(
                navController,
                binding.drawerLayout
            ) || super.onSupportNavigateUp()
        }
    }

    override fun onBackPressed() {
        if(bottomSheet.state == BottomSheetBehavior.STATE_EXPANDED)
            bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        else super.onBackPressed()
    }

    private fun setupNavigation() {
        bottomSheet = BottomSheetBehavior.from(binding.bottomSheet.root)
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
                "fragment_main" -> {
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    setBottomSheetVisibility(View.VISIBLE)
                }
                else -> {
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    binding.bottomSheet.sheetContentContainer.removeAllViewsInLayout()
                    setBottomSheetVisibility(View.GONE)
                }
            }
        }

        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {}
            override fun onDrawerClosed(drawerView: View) {}
            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                (binding.toolbar.navigationIcon as DrawerArrowDrawable).progress = slideOffset
                if (slideOffset > 0 && bottomSheet.state != BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
                }

                if (slideOffset == 0f) binding.floatingButton.show()
                else binding.floatingButton.hide()
            }
        })

        binding.navigation.setNavigationItemSelectedListener {
            drawerListenerItem?.onDrawerMenuItemClicked(it.itemId)
            true
        }

        binding.floatingButton.setOnClickListener {
            if (bottomSheet.state == BottomSheetBehavior.STATE_COLLAPSED)
                bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED

        }

        bottomSheet.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED)
                    binding.floatingButton.show()
                else
                    binding.floatingButton.hide()
            }

        })
    }

    private fun setUpUserView(user: User?) {
        headerBinding.user = user
        headerBinding.avatar.setImageWithURL(user?.avatar)
    }

    fun setDrawerListener(listener: OnDrawerMenuItemClicked) {
        drawerListenerItem = listener
    }

    fun setBottomSheetVisibility(visibility: Int) {
        binding.bottomSheet.sheetContentContainer.removeAllViewsInLayout()
        binding.bottomSheet.root.visibility = visibility
        binding.floatingButton.visibility = visibility
    }

    fun setSheetTitle(title: Int) {
        binding.bottomSheet.sheetTitle.setText(title)
        binding.floatingButton.setText(title)
    }

    fun setSheetTitleDrawable(title: Int) {
        binding.floatingButton.icon = ContextCompat.getDrawable(this, title)
        binding.bottomSheet.sheetTitle.setCompoundDrawablesWithIntrinsicBounds(
            title, 0, 0, 0
        )
    }

    fun setSheetContent(c: View) {
        binding.bottomSheet.sheetContentContainer.addView(c)
    }

    fun closeSheet() {
        bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    interface OnDrawerMenuItemClicked {
        fun onDrawerMenuItemClicked(id: Int)
    }
}