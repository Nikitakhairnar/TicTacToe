package com.example.tictactoe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

var singleUser = false
class MainActivity : AppCompatActivity() {

    lateinit var SinglePlayerBtn : Button
    lateinit var MultiPlayetBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SinglePlayerBtn = findViewById(R.id.idBtnSinglePlayer)
        MultiPlayetBtn = findViewById(R.id.idBtnMultiPlayer)
        SinglePlayerBtn.setOnClickListener {
            singleUser = true
            startActivity(Intent(this, GamePlayActivity::class.java))
        }
        MultiPlayetBtn.setOnClickListener{
            singleUser = false
            startActivity(Intent(this, GamePlayActivity::class.java))
        }
    }
}