package com.example.retrofitproject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.retrofitproject.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainAViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupNavigation()
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        viewModel = ViewModelProviders.of(this, MainAViewModel.Factory(this)).get(MainAViewModel::class.java)
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
        val navController = findNavController(R.id.nav_host_fragment)
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, binding.drawerLayout)
        navigation.setupWithNavController(navController)
    }

    @SuppressLint("SetTextI18n")
    private fun setUpUserView(user: User?){
        binding.user = user

        if(user!!.avatar != null)
            Glide.with(this).load(user.avatar).into(binding.avatar)
        else if(user.gravatar != null)
            Glide.with(this).load(user.gravatar).into(binding.avatar)

    }
}