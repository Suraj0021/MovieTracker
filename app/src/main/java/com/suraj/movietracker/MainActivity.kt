package com.suraj.movietracker

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.suraj.movietracker.databinding.ActivityMainBinding
import com.suraj.movietracker.util.NetworkChangeReceiver

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    private var networkChangeReceiver: NetworkChangeReceiver? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        networkChangeReceiver = NetworkChangeReceiver(this)
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeReceiver, intentFilter)

        binding.bottomNavigation.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController


        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nowPlayingFragment,
                R.id.topRatedFragment,
                R.id.populerFrtagment,
                R.id.upcomingFragment,
                R.id.favouriteFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)



   binding. bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_nowPlaying -> {
                    navController.popBackStack()
                    navController.navigate(R.id.nowPlayingFragment)
                    true
                }

                R.id.navigation_topRated -> {
                    navController.popBackStack()
                    navController.navigate(R.id.topRatedFragment)
                    true
                }

                R.id.navigation_populer -> {
                    navController.popBackStack()
                    navController.navigate(R.id.populerFrtagment)
                    true
                }

                R.id.navigation_upcoming -> {
                    navController.popBackStack()
                    navController.navigate(R.id.upcomingFragment)
                    true
                }

                R.id.navigation_favourite -> {
                    navController.popBackStack()
                    navController.navigate(R.id.favouriteFragment)
                    true
                }


                else -> false

            }
        }
    }

    fun updateUIOnNetworkChange(isConnected: Boolean) {
        if (isConnected) {

        } else {

            navController.navigate(R.id.favouriteFragment)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkChangeReceiver)
    }


}