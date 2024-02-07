package com.example.atm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.system.exitProcess

class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Get values from previous activity
        var balance = intent.getIntExtra("newBalance", 10000)
        var referenceNo = intent.getIntExtra("newReference", 100)


        val btn_bal = findViewById<Button>(R.id.btn_bal)
        val btn_deposit = findViewById<Button>(R.id.btn_deposit)
        val btn_withdraw = findViewById<Button>(R.id.btn_withdraw)
        val btn_transfer = findViewById<Button>(R.id.btn_transfer)
        val btn_logout = findViewById<Button>(R.id.btn_logout)

        btn_bal.setOnClickListener {
            val intent = Intent(this, BalanceInquiry::class.java)
            intent.putExtra("newBalance", balance)
            intent.putExtra("newReference", referenceNo)
            startActivity(intent)
        }

        btn_deposit.setOnClickListener {
            val intent = Intent(this, Deposit::class.java)
            intent.putExtra("newBalance", balance)
            intent.putExtra("newReference", referenceNo)
            startActivity(intent)
        }

        btn_withdraw.setOnClickListener {
            val intent = Intent(this, Withdraw::class.java)
            intent.putExtra("newBalance", balance)
            intent.putExtra("newReference", referenceNo)
            startActivity(intent)
        }

        btn_transfer.setOnClickListener {
            val intent = Intent(this, Transfer::class.java)
            intent.putExtra("newBalance", balance)
            intent.putExtra("newReference", referenceNo)
            startActivity(intent)
        }

        btn_logout.setOnClickListener {
            val intent_logout = Intent(this, MainActivity::class.java)
            startActivity(intent_logout)
//            moveTaskToBack(true)
//            exitProcess(-1)
        }
    }
}