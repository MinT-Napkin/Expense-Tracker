package com.example.expensetracker

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.expensetracker.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import ir.mahozad.android.PieChart


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityMainBinding.inflate(layoutInflater)
     setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

//        binding.appBarMain.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_gallery, R.id.nav_wish_list, R.id.nav_currency), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val pieChart = findViewById<PieChart>(R.id.pieChart)
        pieChart.slices = listOf(
            PieChart.Slice(0.1f, Color.rgb(214, 152, 158), legend = "Personal"),
            PieChart.Slice(0.1f, Color.rgb(171, 152, 158), legend = "Bills"),
            PieChart.Slice(0.1f, Color.rgb(171, 152, 214), legend = "Utilities"),
            PieChart.Slice(0.1f, Color.rgb(171, 214, 214), legend = "Transportation"),
            PieChart.Slice(0.1f, Color.rgb(244, 232, 215), legend = "Food"),
            PieChart.Slice(0.1f, Color.rgb(170, 213, 220), legend = "Entertainment"),
            PieChart.Slice(0.2f, Color.rgb(222, 244, 244), legend = "Gift(s)"),
            PieChart.Slice(0.2f, Color.rgb(244, 222, 220), legend = "Others"),
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}

