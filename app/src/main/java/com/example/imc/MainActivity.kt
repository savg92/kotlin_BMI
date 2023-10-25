package com.example.imc

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val aboutContainer = findViewById<RelativeLayout>(R.id.aboutContainer)
        val aboutButton = findViewById<Button>(R.id.aboutButton)

        val formContainer = findViewById<RelativeLayout>(R.id.formContainer)
        val name = findViewById<EditText>(R.id.nameEditText)
        val height = findViewById<EditText>(R.id.heightEditText)
        val weight = findViewById<EditText>(R.id.weightEditText)
        val calculateButton = findViewById<Button>(R.id.calculateButton)

        val resultContainer = findViewById<RelativeLayout>(R.id.resultContainer)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)
        val calculateAgainBtn = findViewById<Button>(R.id.calculateAgainButton)
        val resetBtn = findViewById<Button>(R.id.resetButton)

        var table = findViewById<TableLayout>(R.id.tableLayout)

        // Get name and result from local storage
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val savedAboutPref = sharedPreferences.getBoolean("about", false)
        val savedName = sharedPreferences.getString("name", null)
        val savedResult = sharedPreferences.getFloat("result", -1f)

        // Show about if it's the first time, else hide it
        if (!savedAboutPref) {
            aboutContainer.visibility = TextView.VISIBLE
        } else {
            aboutContainer.visibility = TextView.GONE
        }

        // Hide about
        aboutButton.setOnClickListener {
            aboutContainer.visibility = TextView.GONE
            val editor = sharedPreferences.edit()
            editor.putBoolean("about", true)
            editor.apply()
        }

        // Show name and result if they exist
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

        // Calculate BMI
        calculateButton.setOnClickListener {
            val heightData = height.text.toString().toDouble() / 100
            val weightData = weight.text.toString().toDouble()
            val result = weightData / (heightData * heightData)
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

            resultContainer.visibility = TextView.VISIBLE
            table.visibility = TextView.VISIBLE
            formContainer.visibility = View.GONE
        }

         // Show form
         calculateAgainBtn.setOnClickListener {
              formContainer.visibility = View.VISIBLE
         }

        // Reset about, name and result
        resetBtn.setOnClickListener {
            aboutContainer.visibility = TextView.VISIBLE
            name.text.clear()
            height.text.clear()
            weight.text.clear()
            resultTextView.text = ""
            val editor = sharedPreferences.edit()
            editor.putBoolean("about", false)
            editor.putString("name", null)
            editor.putFloat("result", -1f)
            editor.apply()
        }
    }
}