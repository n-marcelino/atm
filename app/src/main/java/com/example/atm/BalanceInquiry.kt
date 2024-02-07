package com.example.atm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class BalanceInquiry : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bal)

        // Get values from previous activity
        var balance = intent.getIntExtra("newBalance", 10000)
        val balanceText = findViewById<TextView>(R.id.balance)
        balanceText.setText(balance.toString() +" PHP")
        var referenceNo = intent.getIntExtra("newReference", 100)

        val btn_back = findViewById<Button>(R.id.btn_back)

        btn_back.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            intent.putExtra("newBalance", balance)
            intent.putExtra("newReference", referenceNo)
            startActivity(intent)
        }


    }
}