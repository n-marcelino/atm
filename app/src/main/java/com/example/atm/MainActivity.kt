package com.example.atm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_login = findViewById<Button>(R.id.btn_login)

        btn_login.setOnClickListener {
            val intent_dashboard = Intent(this, Dashboard::class.java)
            startActivity(intent_dashboard)
        }

        // Correct Pin
        val pin = "1234"

        // Number of Tries Available
        var tries = 3

        // Enter Pin Button UI
        val submitButton = findViewById<Button>(R.id.btn_login)
        // Delete PIN Button UI
        val deleteButton = findViewById<Button>(R.id.delete)

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

        // 4 PIN Numbers UI
        val firstNum = findViewById<TextView>(R.id.firstNum)
        val secondNum = findViewById<TextView>(R.id.secondNum)
        val thirdNum = findViewById<TextView>(R.id.thirdNum)
        val fourthNum = findViewById<TextView>(R.id.fourthNum)

        // Set On Click Listeners for all number buttons
        buttonNum0.setOnClickListener(){ writePin(0) }
        buttonNum1.setOnClickListener(){ writePin(1) }
        buttonNum2.setOnClickListener(){ writePin(2) }
        buttonNum3.setOnClickListener(){ writePin(3) }
        buttonNum4.setOnClickListener(){ writePin(4) }
        buttonNum5.setOnClickListener(){ writePin(5) }
        buttonNum6.setOnClickListener(){ writePin(6) }
        buttonNum7.setOnClickListener(){ writePin(7) }
        buttonNum8.setOnClickListener(){ writePin(8) }
        buttonNum9.setOnClickListener(){ writePin(9) }

        // Set On Click Listener for Delete button
        deleteButton.setOnClickListener(){ deletePin(firstNum,secondNum,thirdNum,fourthNum) }

        // Set On Click Listener for Delete button
        deleteButton.setOnClickListener(){ deletePin(firstNum,secondNum,thirdNum,fourthNum) }

        // Set On Click Listener for Submit Button
        submitButton.setOnClickListener {

            // Concatenate PIN numbers into one string
            val enteredPIN = firstNum.text.toString() + secondNum.text.toString() + thirdNum.text.toString() + fourthNum.text.toString()

            // Make Alert Dialog
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)

            // Check if there are more tries
            if (tries <= 1) {
                builder
                    .setMessage("You have 0 tries left! App will be closing now")
                    .setTitle("You have no tries left!")
                    .setPositiveButton("Exit App") { dialog, which ->
                        moveTaskToBack(true);
                        exitProcess(-1)
                    }
            }

            // Check if PIN is complete
            else if (firstNum.text.toString() == "_" || secondNum.text.toString() == "_" || thirdNum.text.toString() == "_" || fourthNum.text.toString() == "_") {
                builder
                    .setMessage("Please insert complete PIN")
                    .setTitle("Incomplete PIN!")
                    .setPositiveButton("Continue") { dialog, which ->
                    }
            }

            // Check if PIN is correct
            else if (enteredPIN == pin) {
                builder
                    .setMessage("You have successfully entered the correct pin number")
                    .setTitle("Entered PIN Success!")
                    .setPositiveButton("Continue") { dialog, which ->
                        startActivity(Intent(this, Dashboard::class.java))
                    }
            }

            // If PIN is wrong
            else {
                tries--
                builder
                    .setMessage("You have entered the wrong pin number")
                    .setTitle("Wrong PIN Entered")
                    .setPositiveButton("Try Again") { dialog, which ->
                        Toast.makeText(
                            this,
                            "You have " + tries + " tries left",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                repeat(4) {
                    deletePin(firstNum,secondNum,thirdNum,fourthNum)
                }
            }

            //Show Alert Dialog
            val dialog: AlertDialog = builder.create()
            dialog.setCanceledOnTouchOutside(false);
            dialog.show()
        }


    }

    //Writes numbers in the PIN field
    fun writePin(num: Int){

        // 4 PIN Numbers UI
        val firstNum = findViewById<TextView>(R.id.firstNum)
        val secondNum = findViewById<TextView>(R.id.secondNum)
        val thirdNum = findViewById<TextView>(R.id.thirdNum)
        val fourthNum = findViewById<TextView>(R.id.fourthNum)


        if (firstNum.text.toString() != "_") {
            if (secondNum.text.toString() != "_") {
                if (thirdNum.text.toString() != "_") {
                    if (fourthNum.text.toString() == "_")  fourthNum.setText(num.toString())
                } else thirdNum.setText(num.toString())
            } else secondNum.setText(num.toString())
        } else firstNum.setText(num.toString())

    }

    //Backspace for PIN field
    fun deletePin(firstNum : TextView, secondNum : TextView, thirdNum : TextView, fourthNum : TextView){
        if (fourthNum.text.toString() != "_") fourthNum.setText("_")
        else{
            if (thirdNum.text.toString() != "_") thirdNum.setText("_")
            else{
                if (secondNum.text.toString() != "_") secondNum.setText("_")
                else{
                    if (firstNum.text.toString() != "_") firstNum.setText("_")
                }
            }
        }
    }
}