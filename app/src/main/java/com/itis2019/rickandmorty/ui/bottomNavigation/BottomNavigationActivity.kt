package com.itis2019.rickandmorty.ui.bottomNavigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.itis2019.rickandmorty.R
import kotlinx.android.synthetic.main.activity_bottom_navigation.*

class BottomNavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)
        val navController = findNavController(this, R.id.host_fragment)
        NavigationUI.setupWithNavController(bnv_navigation, navController)
    }
}
