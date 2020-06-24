package com.tustar.demo.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.tustar.annotation.GROUP_HEN_ID
import com.tustar.annotation.RowDemo
import com.tustar.annotation.RowGroup
import com.tustar.demo.R

@RowGroup(id = GROUP_HEN_ID, name = R.string.group_hencoder)
@RowDemo(groupId = GROUP_HEN_ID, name = R.string.hen_practice_draw_1,
        actionId = R.id.action_main_to_draw1)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.main_host_fragment)
        val appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}
