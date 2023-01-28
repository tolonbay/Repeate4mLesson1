package com.example.repeate4mlesson1

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.repeate4mlesson1.databinding.ActivityMainBinding
import com.example.repeate4mlesson1.utilits.Preferences

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_profile

            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        if (Preferences(this).getHaveSeenOnBoarding()){
            navController.navigate(
                R.id.navigation_home)
        }else{
            navController.navigate(
                R.id.onBoardFragment
            )
        }




        navController.addOnDestinationChangedListener{ _, des, _ ->
            navView.visibility =
                if (des.id == R.id.newTaskFragment|| des.id == R.id.onBoardFragment) View.GONE else View.VISIBLE
        }
    }
}