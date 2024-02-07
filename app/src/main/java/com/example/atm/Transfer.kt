package com.example.atm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class Transfer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

        // Make variables for delete, submit and back to dashboard buttons
        val deleteButton = findViewById<Button>(R.id.delete)
        val submitButton = findViewById<Button>(R.id.btn_transfer)
        val backButton = findViewById<Button>(R.id.btn_back)

        // Get values from previous activity
        var balance = intent.getIntExtra("newBalance", 10000)
        val balanceText = findViewById<TextView>(R.id.balance)
        var referenceNo = intent.getIntExtra("newReference", 100)

        // Change Balance Amount Text based on current balance
        balanceText.setText(balance.toString() +" PHP")

        // Make variables for fields
        val transferForm = findViewById<EditText>(R.id.editTextNumber)
        val accountForm = findViewById<EditText>(R.id.accountNo)
        val receiverForm = findViewById<EditText>(R.id.receiver)
        transferForm.setShowSoftInputOnFocus(false)
        transferForm.inputType = InputType.TYPE_NULL
        accountForm.keyListener = null //Prevents keyboard input on account form

        // Numpad UI
        val numericButtonIds = arrayOf(
            R.id.buttonNum0, R.id.buttonNum1, R.id.buttonNum2,
            R.id.buttonNum3, R.id.buttonNum4, R.id.buttonNum5,
            R.id.buttonNum6, R.id.buttonNum7, R.id.buttonNum8, R.id.buttonNum9
        )

        // Set Onclick Listeners
        // This allows numpad to work on both accountForm and transferForm
        for (buttonId in numericButtonIds) {
            findViewById<Button>(buttonId).setOnClickListener { button ->
                if (currentFocus == null) {
                    accountForm.requestFocus() // Set focus to the first EditText if no EditText is currently focused
                }
                val focusedEditText = currentFocus as? EditText
                focusedEditText?.let {
                    val number = (button as Button).text.toString().toInt()
                    writeForm(number, it)
                    it.setSelection(it.text.length)
                }
            }
        }

        // Set On Click Listener for delete button
        deleteButton.setOnClickListener {
            // Get the currently focused EditText
            val focusedEditText = currentFocus as? EditText

            // If an EditText is focused, reduce its form
            focusedEditText?.let {
                reduceForm(it)
            }
        }


        //deleteButton.setOnClickListener(){ reduceForm(transferForm) }

        // Set On Click Listener for Submit Button
        submitButton.setOnClickListener {

            // Make Alert Dialog
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)

            // Checks if Fields are all complete
            if(accountForm.text.toString() == ""){
                Toast.makeText(
                    this,
                    "Please insert account number of receiver",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if(receiverForm.text.toString() == ""){
                Toast.makeText(
                    this,
                    "Please insert a receiver",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if(transferForm.text.toString() == ""){
                Toast.makeText(
                    this,
                    "Please insert amount first",
                    Toast.LENGTH_SHORT
                ).show()
            } // If All Fields Are Complete, Show Alert Dialog
            else{
                builder
                    .setMessage(
                        "Reference No.: " + referenceNo +
                                "\nDate: " + SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                            Calendar.getInstance().time) +
                                "\nAccount No.: " + accountForm.text.toString() +
                                "\nName: " + receiverForm.text.toString() +
                                "\nAmount: " + transferForm.text.toString() + " PHP")

                    .setTitle("Confirm Details Before Continuing!")
                    .setPositiveButton("Continue") { dialog, which ->
                        balance = balance - transferForm.text.toString().toInt()
                        balanceText.setText(balance.toString() +" PHP")

                        Toast.makeText(
                            this,
                            "Your new balance is:" + balance.toString() + " PHP",
                            Toast.LENGTH_SHORT
                        ).show()
                        referenceNo += 1
                        transferForm.setText("")
                        accountForm.setText("")
                        receiverForm.setText("")
                    }
                    .setNegativeButton("Cancel") { dialog, which ->
                    }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }

        // Go Back to Dashboard
        backButton.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            intent.putExtra("newBalance", balance)
            intent.putExtra("newReference", referenceNo)
            startActivity(intent)
        }
    }

    // Function to let the number button UI write in the deposit amount field
    fun writeForm(num : Int, amount : EditText){
        amount.setText(amount.text.toString() + num.toString())
    }

    // Function to let the button UI delete(backspace) in the transfer amount field
    fun reduceForm(amount : EditText){
        amount.setText(amount.text.toString().dropLast(1))
    }
}