package com.example.retrofitproject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
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
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupNavigation()
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        getUser()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
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

    private fun getUser() {
        scope.launch {
            try {
                val response = client.getUserDetail(getToken()!!)
                if (response.isSuccessful)
                    setUpUserView(response.body())
                else
                    processResponseCode(response.code())

            } catch (t: Throwable) {
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpUserView(user: User?){
//        name.text = "Username: ${user?.username}"
//        username.text = "Name: ${user?.name}"
//        url.text = "URL: ${user?.url}"
//        blog.text = "Blog: ${user?.blog}"
        binding.user = user

        if(user!!.avatar != null)
            Glide.with(this).load(user.avatar).into(binding.avatar)
        else if(user.gravatar != null)
            Glide.with(this).load(user.gravatar).into(binding.avatar)

    }
}