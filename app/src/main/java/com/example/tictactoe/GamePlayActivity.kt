package com.example.tictactoe

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

var playerTurn = true
class GamePlayActivity : AppCompatActivity() {
    lateinit var player1Tv : TextView
    lateinit var player2Tv : TextView
    lateinit var box1Btn : Button
    lateinit var box2Btn : Button
    lateinit var box3Btn : Button
    lateinit var box4Btn : Button
    lateinit var box5Btn : Button
    lateinit var box6Btn : Button
    lateinit var box7Btn : Button
    lateinit var box8Btn : Button
    lateinit var box9Btn : Button
    lateinit var resetBtn : Button
    var player1count = 0
    var player2count = 0
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var emptyCells = ArrayList<Int>()
    var activeUSer = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_play)
        player1Tv = findViewById(R.id.idTVPlayer1)
        player2Tv = findViewById(R.id.idTVPlayer2)
        box1Btn = findViewById(R.id.idBtnBox1)
        box2Btn = findViewById(R.id.idBtnBox2)
        box3Btn = findViewById(R.id.idBtnBox3)
        box4Btn = findViewById(R.id.idBtnBox4)
        box5Btn = findViewById(R.id.idBtnBox5)
        box6Btn = findViewById(R.id.idBtnBox6)
        box7Btn = findViewById(R.id.idBtnBox7)
        box8Btn = findViewById(R.id.idBtnBox8)
        box9Btn = findViewById(R.id.idBtnBox9)
        resetBtn = findViewById(R.id.idBtnReset)
        resetBtn.setOnClickListener {
            reset()
        }
    }

    private fun reset() {
        player1.clear()
        player2.clear()
        emptyCells.clear()
        for(i in 1..9){
            var buttonSelected : Button
            buttonSelected = when(i){
                1->box1Btn
                2->box2Btn
                3->box3Btn
                4->box4Btn
                5->box5Btn
                6->box6Btn
                7->box7Btn
                8->box8Btn
                9->box9Btn
                else->{
                    box1Btn
                }
            }
            buttonSelected.isEnabled = true
            buttonSelected.text = " "
            player1Tv.text = "player 1 : $player1count"
            player2Tv.text = "player 2 : $player2count"
        }
    }

    fun buttonClick(view: View) {
        if(playerTurn){
            val but = view as Button
            var cellId = 0
            when(but.id){
                R.id.idBtnBox1 -> cellId = 1
                R.id.idBtnBox2 -> cellId = 2
                R.id.idBtnBox3 -> cellId = 3
                R.id.idBtnBox4 -> cellId = 4
                R.id.idBtnBox5 -> cellId = 5
                R.id.idBtnBox6 -> cellId = 6
                R.id.idBtnBox7 -> cellId = 7
                R.id.idBtnBox8 -> cellId = 8
                R.id.idBtnBox9 -> cellId = 9
            }
            playerTurn = false
            Handler().postDelayed({playerTurn = true}, 600)
            playNow(but,cellId)
        }
    }

    private fun playNow(buttonSelected: Button, currentCell: Int) {
        if(activeUSer == 1){
            buttonSelected.text = "x"
            buttonSelected.setTextColor(Color.parseColor("#EC0C0C"))
            player1.add(currentCell)
            emptyCells.add(currentCell)
            buttonSelected.isEnabled = false
            val checkWinner = checkWinner()
            if(checkWinner == 1){
                Handler().postDelayed(Runnable { reset() },2000)
            }else if(singleUser){
                Handler().postDelayed(Runnable { robot()}, 200)
            }else{
                activeUSer = 1
            }
        }else{
            buttonSelected.text = "O"
            buttonSelected.setTextColor(Color.parseColor("D22BB8884"))
            activeUSer = 1
            player2.add(currentCell)
            emptyCells.add(currentCell)
            buttonSelected.isEnabled = false
            val checkWinner = checkWinner()
            if(checkWinner == 1){
                Handler().postDelayed(Runnable { reset() }, 4000)
            }
        }
    }

    private fun robot() {
        val rnd = (1..9).random()
        if(emptyCells.contains(rnd)){
            robot()
        }else{
            val buttonSelected = when(rnd){
                1->box1Btn
                2->box2Btn
                3->box3Btn
                4->box4Btn
                5->box5Btn
                6->box6Btn
                7->box7Btn
                8->box8Btn
                9->box9Btn
                else ->{
                    box1Btn
                }
            }
            emptyCells.add(rnd)
            buttonSelected.text = "O"
            buttonSelected.setTextColor(Color.parseColor("#D22BB804"))
            player2.add(rnd)
            buttonSelected.isEnabled = false
            var checkWinner = checkWinner()
            if(checkWinner == 1){
                Handler().postDelayed(Runnable { reset() }, 2000)
            }
        }
    }

    private fun checkWinner() : Int{
        if((player1.contains(1) && player1.contains(2) && player1.contains(3))||
                (player1.contains(1) && player1.contains(4) && player1.contains(7))||
                (player1.contains(3) && player1.contains(6) && player1.contains(9))||
                (player1.contains(7) && player1.contains(8) && player1.contains(9))||
                (player1.contains(4) && player1.contains(5) && player1.contains(6))||
                (player1.contains(1) && player1.contains(5) && player1.contains(9))||
                (player1.contains(3) && player1.contains(5) && player1.contains(7))||
                (player1.contains(2) && player1.contains(5) && player1.contains(8))
                ){
            player1count+=1
            buttonDisable()
            disableReset()
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Player 1 wins!!\n\n"+"Do you want to play again?")
            build.setPositiveButton("ok"){dialog, which->
                reset()
            }
            build.setNegativeButton("Exit"){dialog, which->
                exitProcess(1)
            }
            Handler().postDelayed(Runnable { build.show() }, 2000)
            return 1
        }else if((player2.contains(1) && player2.contains(2) && player2.contains(3))||
                (player2.contains(1) && player2.contains(4) && player2.contains(7))||
                (player2.contains(3) && player2.contains(6) && player2.contains(9))||
                (player2.contains(7) && player2.contains(8) && player2.contains(9))||
                (player2.contains(4) && player2.contains(5) && player2.contains(6))||
                (player2.contains(1) && player2.contains(5) && player2.contains(9))||
                (player2.contains(3) && player2.contains(5) && player2.contains(7))||
                (player2.contains(2) && player2.contains(5) && player2.contains(8))
                ){
            player2count+=1
            buttonDisable()
            disableReset()
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Player 2 wins!!\n\n"+"Do you want to play again?")
            build.setPositiveButton("ok"){dialog, which->
                reset()
            }
            build.setNegativeButton("Exit"){dialog, which->
                exitProcess(1)
            }
            Handler().postDelayed(Runnable { build.show() }, 2000)
            return 1
        }else if(emptyCells.contains(1) && emptyCells.contains(2) && emptyCells.contains(3) && emptyCells.contains(4)
                && emptyCells.contains(5) && emptyCells.contains(6) && emptyCells.contains(7)&& emptyCells.contains(8)
                && emptyCells.contains(9)){
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Game Draw\n\n"+"Do you want to play again?")
            build.setPositiveButton("ok"){dialog, which->
                reset()
            }
            build.setNegativeButton("Exit"){dialog, which->
                exitProcess(1)
            }
            build.show()
            return 1
        }
        return 0
    }

    private fun buttonDisable(){
        player1.clear()
        player2.clear()
        emptyCells.clear()
        activeUSer = 1
        for(i in 1..9){
            var buttonSelected : Button
            buttonSelected = when(i){
                1->box1Btn
                2->box2Btn
                3->box3Btn
                4->box4Btn
                5->box5Btn
                6->box6Btn
                7->box7Btn
                8->box8Btn
                9->box9Btn
                else->{
                    box1Btn
                }
            }
            buttonSelected.isEnabled = true
            buttonSelected.text = " "
            player1Tv.text = "player 1 : $player1count"
            player2Tv.text = "player 2 : $player2count"
        }
    }

    private fun disableReset() {
        resetBtn.isEnabled = false
        Handler().postDelayed(Runnable { resetBtn.isEnabled = true }, 2200)
    }
}