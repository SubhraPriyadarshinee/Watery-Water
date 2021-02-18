package com.mypractice.waterywater

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
//    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
         navController = navHostFragment.navController

//        navController=findNavController(R.id.fragment)
        drawerLayout=findViewById(R.id.drawerLayout)
        navView.setupWithNavController(navController)

        appBarConfiguration= AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {

        val navController = findNavController(R.id.fragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }



}