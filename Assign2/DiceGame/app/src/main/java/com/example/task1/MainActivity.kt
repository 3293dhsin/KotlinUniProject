package com.example.task1

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private var player1textView: TextView?=null
    private var player2textView: TextView?=null
    private var image1View: ImageView?=null
    private var image2View: ImageView?=null
    private var dice1Number=0
    private var dice2Number=0
    private lateinit var player1textViewNum: TextView
    private lateinit var player2textViewNum: TextView
    private var countPlayer1=0
    private var countPlayer2=0
    private var count=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
    }

    private fun initUI() {
        player1textView=findViewById(R.id.player1_textview)
        player2textView=findViewById(R.id.player2_textview)
        image1View=findViewById(R.id.imageView)
        image2View=findViewById(R.id.imageView2)
        player1textViewNum=findViewById(R.id.player1_num)
        player2textViewNum=findViewById(R.id.player2_num)
        if (count == 1)
            changeTextColor (count)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawDice(count)
        count++
        changeTextColor (count)
    }

    private fun drawDice(count:Int) {
        dice1Number = changeImage(image1View)
        dice2Number = changeImage(image2View)
        var sum = dice1Number + dice2Number

        if (count % 2 != 0 && sum % 2 == 0) {
            countPlayer1 += sum
        } else if (count % 2 == 0 && sum % 2 != 0) {
            countPlayer2 += sum
        }

        player1textViewNum.text = "$countPlayer1"
        player2textViewNum.text = "$countPlayer2"

    }

    private fun changeImage(diceImage: ImageView?):Int {
        var num = (1..6).random()
        when (num) {
            1 -> diceImage?.setImageResource(R.drawable.dice_one)
            2 -> diceImage?.setImageResource(R.drawable.dice_two)
            3 -> diceImage?.setImageResource(R.drawable.dice_three)
            4 -> diceImage?.setImageResource(R.drawable.dice_four)
            5 -> diceImage?.setImageResource(R.drawable.dice_five)
            else -> diceImage?.setImageResource(R.drawable.dice_six)
        }
        return num
    }

    private fun changeTextColor (count:Int) {
        if (count % 2 == 0) {
            player1textView?.setTextColor(ContextCompat.getColor(this, R.color.black))
            player2textView?.setTextColor(ContextCompat.getColor(this, R.color.red))
        } else if (count % 2 != 0) {
            player1textView?.setTextColor(ContextCompat.getColor(this, R.color.red))
            player2textView?.setTextColor(ContextCompat.getColor(this, R.color.black))
        }
    }

}