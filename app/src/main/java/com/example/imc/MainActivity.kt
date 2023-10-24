package com.example.imc

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val aboutContainer = findViewById<TextView>(R.id.aboutContainer)
        val aboutButton = findViewById<Button>(R.id.welcomeContainer)

        val name = findViewById<EditText>(R.id.nameEditText)
        val height = findViewById<EditText>(R.id.heightEditText)
        val weight = findViewById<EditText>(R.id.weightEditText)
        val calculateButton = findViewById<Button>(R.id.calculateButton)

        val resultTextView = findViewById<TextView>(R.id.resultTextView)

        // Get name and result from local storage
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val savedName = sharedPreferences.getString("name", null)
        val savedResult = sharedPreferences.getFloat("result", -1f)

        if (savedName != null && savedResult != -1f) {
            resultTextView.text = when {
                savedResult <= 18.5 -> {
                    savedName + " be careful your BMI is: %.2f".format(savedResult) + " (Underweight)"
                }
                savedResult <= 24.9 -> {
                    savedName + " congratulations your BMI is: %.2f".format(savedResult) + " (Normal)"
                }
                savedResult <= 29.9 -> {
                    savedName + " be careful your BMI is: %.2f".format(savedResult) + " (Overweight)"
                }
                else -> {
                    savedName + " be careful your BMI is: %.2f".format(savedResult) + " (Obesity)"
                }
            }
        }

        calculateButton.setOnClickListener {
            val height = height.text.toString().toDouble() / 100
            val weight = weight.text.toString().toDouble()
            val result = weight / (height * height)
            val formattedResult = 
                when {
                    result <= 18.5 -> {
                        name.text.toString() + " be careful your BMI is: %.2f".format(result) + " (Underweight)"
                    }

                    result <= 24.9 -> {
                        name.text.toString() + " congratulations your BMI is: %.2f".format(result) + " (Normal)"
                    }

                    result <= 29.9 -> {
                        name.text.toString() + " be careful your BMI is: %.2f".format(result) + " (Overweight)"
                    }

                    else -> {
                        name.text.toString() + " be careful your BMI is: %.2f".format(result) + " (Obesity)"
                    }
                }
            resultTextView.text = formattedResult

             // Save name and result in local storage
            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("name", name.text.toString())
            editor.putFloat("result", result.toFloat())
            editor.apply()
        }
    }
}