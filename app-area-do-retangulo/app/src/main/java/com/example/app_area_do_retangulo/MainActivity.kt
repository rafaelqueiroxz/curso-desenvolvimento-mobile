package com.example.app_area_do_retangulo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var enterBase : EditText
    private lateinit var enterHeight : EditText
    private lateinit var answer : TextView
    private lateinit var calculateButton : Button
    private lateinit var cleanButton : Button
    private lateinit var exitButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Associa as variáveis com os componentes da tela

        enterBase = findViewById(R.id.enterBase)
        enterHeight = findViewById(R.id.enterHeight)
        answer = findViewById(R.id.answer)
        calculateButton = findViewById(R.id.calculateButton)
        cleanButton = findViewById(R.id.cleanButton)
        exitButton = findViewById(R.id.exitButton)

        calculateButton.setOnClickListener {
            // Declaração da variáveis
            var base : Double
            var height : Double
            var area : Double

            // Lógica do calculo
            base = enterBase.text.toString().toDouble()
            height = enterHeight.text.toString().toDouble()
            area = base * height
            answer.text = area.toString()

        }

        cleanButton.setOnClickListener {
            enterBase.setText("")
            enterHeight.setText("")
            answer.text = ""
        }

        exitButton.setOnClickListener {
            finishAndRemoveTask()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}