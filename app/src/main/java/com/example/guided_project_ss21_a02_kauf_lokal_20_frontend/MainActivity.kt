package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) setTheme(R.style.nightly)
        else setTheme(R.style.lightly)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        setSupportActionBar(findViewById<MaterialToolbar>(R.id.toolbarTop))
        setupBottomNavMenu(navController)
        setupDarkMode()
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setupWithNavController(navController)
    }

    private fun setupActionBar() {
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home, R.id.news, R.id.vendor, R.id.coupon)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    private fun setupDarkMode() {
        val settingsPreferences: SharedPreferences = getSharedPreferences("AppSettingPreferences", 0)
        val isNightModeOn: Boolean = settingsPreferences.getBoolean("NightMode", false)
        val editPreferences: SharedPreferences.Editor = settingsPreferences.edit()

        //if (isNightModeOn) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        //else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val darkModeSwitch = findViewById<SwitchCompat>(R.id.darkModeSwitch)

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editPreferences.putBoolean("Night/DarkMode", true).apply()
                Toast.makeText(applicationContext, "NightMode Enabled", Toast.LENGTH_SHORT).show()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editPreferences.putBoolean("NightMode", false).apply()
                Toast.makeText(applicationContext, "NightMode Disabled", Toast.LENGTH_SHORT).show()
            }
        }

        darkModeSwitch.isChecked = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
    }
}