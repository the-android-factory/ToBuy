package com.dmp.tobuy.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.dmp.tobuy.R
import com.dmp.tobuy.arch.ToBuyViewModel
import com.dmp.tobuy.database.AppDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: ToBuyViewModel by viewModels()
        viewModel.init(AppDatabase.getDatabase(this))
    }
}