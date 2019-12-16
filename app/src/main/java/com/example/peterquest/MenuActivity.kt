package com.example.peterquest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnPlay.setOnClickListener(){
            val intent = Intent(this,
                MainActivity::class.java)
            startActivity(intent)
        }

        btnAbout.setOnClickListener(){
            val intent = Intent(this,
                AboutActivity::class.java)
            startActivity(intent)
        }

    }
}
