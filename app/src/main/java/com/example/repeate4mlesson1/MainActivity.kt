package com.example.repeate4mlesson1

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.repeate4mlesson1.databinding.ActivityMainBinding
import com.example.repeate4mlesson1.utilits.MainApplication
import com.example.repeate4mlesson1.utilits.Preferences
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseMessaging.getInstance().token.addOnCompleteListener{
            Log.d("Test token",it.result)
        }


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.newTaskFragment,
                R.id.navigation_profile,
                R.id.onBoardFragment,
                R.id.authFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        lifecycleScope.launch {
//            val session = MainApplication.appDatabase?.sessionDao?.get()
//
//            if(session == null){
//                navController.navigate(R.id.authFragment)
//            }else
//            {
//                if (Preferences(this@MainActivity).getHaveSeenOnBoarding()){
//                    navController.navigate(
//                        R.id.navigation_home)
//                }else{
//                    navController.navigate(
//                        R.id.onBoardFragment
//                    )
//                }
//
//            }
//        }

        fun checkAuth(){
           if (Firebase.auth.currentUser == null){
                navController.navigate(
                    R.id.authFragment
                )
            } else {
                navController.navigate(
                    R.id.navigation_home
                )
            }
        }

        if (Preferences(this).getHaveSeenOnBoarding()) {
            checkAuth()
        }else {
            checkAuth()
        }

         navController.addOnDestinationChangedListener { _, des, _ ->
            navView.visibility =
                if (des.id == R.id.newTaskFragment || des.id == R.id.onBoardFragment|| des.id == R.id.authFragment) View.GONE else View.VISIBLE
        }
    }
}