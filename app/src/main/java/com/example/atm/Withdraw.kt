package com.example.atm

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Withdraw : AppCompatActivity() {

    private var currentBalance: Double = 10000.0
    private lateinit var editTextNumber: EditText
    private lateinit var balanceTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdraw)

        editTextNumber = findViewById(R.id.editTextNumber)
        balanceTextView = findViewById(R.id.balance)

        // Set the initial balance amount in the TextView
        updateBalanceText()

        // Set click listeners for numeric buttons
        setNumericButtonClickListeners()

        // Set click listener for the "Delete" button
        findViewById<Button>(R.id.delete).setOnClickListener {
            onDeleteButtonClick()
        }

        // Set click listener for the "Withdraw" button
        findViewById<Button>(R.id.btn_withdraw).setOnClickListener {
            onWithdrawButtonClick()
        }

        val btn_back = findViewById<Button>(R.id.btn_back)

        btn_back.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }
    }
    private fun setNumericButtonClickListeners() {
        val numericButtonIds = arrayOf(
            R.id.buttonNum0, R.id.buttonNum1, R.id.buttonNum2,
            R.id.buttonNum3, R.id.buttonNum4, R.id.buttonNum5,
            R.id.buttonNum6, R.id.buttonNum7, R.id.buttonNum8, R.id.buttonNum9
        )

        for (buttonId in numericButtonIds) {
            findViewById<Button>(buttonId).setOnClickListener {
                onNumericButtonClick(it)
            }
        }
    }

    private fun onNumericButtonClick(view: View) {
        val buttonText = (view as Button).text.toString()
        editTextNumber.append(buttonText)
    }

    private fun onDeleteButtonClick() {
        val currentText = editTextNumber.text.toString()
        if (currentText.isNotEmpty()) {
            val updatedText = currentText.substring(0, currentText.length - 1)
            editTextNumber.text.clear()
            editTextNumber.text.insert(0, updatedText)
        }
    }

    private fun onWithdrawButtonClick() {
        val amountText = editTextNumber.text.toString()
        if (amountText.isNotEmpty()) {
            val withdrawAmount = amountText.toDouble()
            if (withdrawAmount <= currentBalance) {
                currentBalance -= withdrawAmount
                updateBalanceText()
                // You can add additional logic for withdraw functionality here
                // For example, update the backend or show a success message
                Toast.makeText(this@Withdraw, "Withdrawal successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@Withdraw, "Insufficient balance", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this@Withdraw, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateBalanceText() {
        balanceTextView.text = String.format("%.2f PHP", currentBalance)
    }
}
