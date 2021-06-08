package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Speichern der BottomNavigation in Variable
        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        //Finden des NavControllers des NavHosts
        val navController = findNavController(navView)
        //NavBar zur Nutzung des NavControllers ausstatten
        navView.setupWithNavController(navController)

    }
}