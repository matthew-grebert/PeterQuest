package com.example.peterquest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnUp.setOnClickListener{
            boardView.moveAvatar("up");
        }
        btnDown.setOnClickListener{
            boardView.moveAvatar("down")
        }
        btnRight.setOnClickListener{
            boardView.moveAvatar("right")
        }
        btnLeft.setOnClickListener{
            boardView.moveAvatar("left")
        }
        btnAttack.setOnClickListener{
            boardView.attack()
        }

    }




}
