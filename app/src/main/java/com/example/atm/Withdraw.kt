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
import androidx.appcompat.app.AlertDialog

class Withdraw : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdraw)

        // Get values from previous activity
        var balance = intent.getIntExtra("newBalance", 10000)
        val balanceText = findViewById<TextView>(R.id.balance)
        balanceText.setText(balance.toString() +" PHP")
        var referenceNo = intent.getIntExtra("newReference", 100)

        // Buttons
        val deleteButton = findViewById<Button>(R.id.delete)
        val submitButton = findViewById<Button>(R.id.btn_withdraw)
        val backButton = findViewById<Button>(R.id.btn_back)

        // Make withdraw field variable
        val withdrawForm = findViewById<EditText>(R.id.editTextNumber)
        withdrawForm.setShowSoftInputOnFocus(false)

        // Set click listeners for numeric buttons
        // NumPad UI
        val buttonNum0 = findViewById<Button>(R.id.buttonNum0)
        val buttonNum1 = findViewById<Button>(R.id.buttonNum1)
        val buttonNum2 = findViewById<Button>(R.id.buttonNum2)
        val buttonNum3 = findViewById<Button>(R.id.buttonNum3)
        val buttonNum4 = findViewById<Button>(R.id.buttonNum4)
        val buttonNum5 = findViewById<Button>(R.id.buttonNum5)
        val buttonNum6 = findViewById<Button>(R.id.buttonNum6)
        val buttonNum7 = findViewById<Button>(R.id.buttonNum7)
        val buttonNum8 = findViewById<Button>(R.id.buttonNum8)
        val buttonNum9 = findViewById<Button>(R.id.buttonNum9)

        // Set on click listener for all number button
        buttonNum0.setOnClickListener(){ writeForm(0, withdrawForm) }
        buttonNum1.setOnClickListener(){ writeForm(1, withdrawForm) }
        buttonNum2.setOnClickListener(){ writeForm(2, withdrawForm) }
        buttonNum3.setOnClickListener(){ writeForm(3, withdrawForm) }
        buttonNum4.setOnClickListener(){ writeForm(4, withdrawForm) }
        buttonNum5.setOnClickListener(){ writeForm(5, withdrawForm) }
        buttonNum6.setOnClickListener(){ writeForm(6, withdrawForm) }
        buttonNum7.setOnClickListener(){ writeForm(7, withdrawForm) }
        buttonNum8.setOnClickListener(){ writeForm(8, withdrawForm) }
        buttonNum9.setOnClickListener(){ writeForm(9, withdrawForm) }

        // Set on click listener for delete button
        deleteButton.setOnClickListener(){ reduceForm(withdrawForm) }

        // Set on click listener for submit button
        submitButton.setOnClickListener {

            //Makes Alert Dialog
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)

            // Checks if withdraw amount is empty or higher than balance
            if(withdrawForm.text.toString() == ""){
                Toast.makeText(
                    this,
                    "Please insert amount first",
                    Toast.LENGTH_SHORT
                ).show()
            } else if(withdrawForm.text.toString().toInt() > balance){
                Toast.makeText(
                    this,
                    "Withdraw amount higher than balance",
                    Toast.LENGTH_SHORT
                ).show()
            }

            // Shows alert dialog once withdraw amount is submitted
            else{
                builder
                    .setMessage("You will withdraw: "+ withdrawForm.text.toString()+ " PHP")
                    .setTitle("Confirm Details Before Continuing!")
                    .setPositiveButton("Continue") { dialog, which ->
                        balance = balance - withdrawForm.text.toString().toInt()
                        balanceText.setText(balance.toString() +" PHP")
                        withdrawForm.setText("")
                        Toast.makeText(
                            this,
                            "Your new balance is:" + balance + " PHP",
                            Toast.LENGTH_SHORT
                        ).show()
                    }.setNegativeButton("Cancel") { dialog, which ->
                    }
                val dialog: AlertDialog = builder.create()
                dialog.show()

            }
        }

        //Return to dashboard
        backButton.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            intent.putExtra("newBalance", balance)
            intent.putExtra("newReference", referenceNo)
            startActivity(intent)
        }
    }

    // Function to let the number button UI write in the withdraw amount field
    fun writeForm(num : Int, amount : EditText){
        amount.setText(amount.text.toString() + num.toString())
    }

    // Function to let the button UI delete(backspace) in the withdraw amount field
    fun reduceForm(amount : EditText){
        amount.setText(amount.text.toString().dropLast(1))
    }
}
