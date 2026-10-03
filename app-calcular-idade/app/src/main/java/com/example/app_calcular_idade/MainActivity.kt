package com.example.app_calcular_idade

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var enterAnoAtual : EditText
    private lateinit var enterAnoNasc : EditText
    private lateinit var setMostrarAnos : RadioButton
    private lateinit var setMostrarMeses : RadioButton
    private lateinit var setMostrarDias : RadioButton
    private lateinit var setMostrarHoras : RadioButton
    private lateinit var setMostrarMinutos : RadioButton
    private lateinit var setMostrarSegundos : RadioButton
    private lateinit var btnCalcular : Button
    private lateinit var btnLimpar : Button
    private lateinit var btnSair : Button
    private lateinit var tvExibirIdade : TextView

    private lateinit var radioGroup : RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        enterAnoAtual = findViewById(R.id.etAnoAtual)
        enterAnoNasc = findViewById(R.id.etAnoNasc)
        radioGroup = findViewById(R.id.radioGroup)
        setMostrarAnos = findViewById(R.id.rbMostrarAnos)
        setMostrarMeses = findViewById(R.id.rbMostrarMeses)
        setMostrarDias = findViewById(R.id.rbMostrarDias)
        setMostrarHoras = findViewById(R.id.rbMostrarHoras)
        setMostrarMinutos = findViewById(R.id.rbMostrarMinutos)
        setMostrarSegundos = findViewById(R.id.rbMostrarSegundos)
        btnCalcular = findViewById(R.id.btnCalcular)
        btnLimpar = findViewById(R.id.btnLimpar)
        btnSair = findViewById(R.id.btnSair)
        tvExibirIdade = findViewById(R.id.tvExibirIdade)

        btnCalcular.setOnClickListener {

            var anoAtual : Int
            var anoNasc : Int
            var idadeAnos : Int
            var idadeMeses : Int
            var idadeDias : Int
            var idadeHoras : Int
            var idadeMinutos : Int
            var idadeSegundos : Int

            anoAtual = enterAnoAtual.text.toString().toInt()
            anoNasc = enterAnoNasc.text.toString().toInt()
            idadeAnos = anoAtual - anoNasc
            idadeMeses = idadeAnos * 12
            idadeDias = idadeAnos * 365
            idadeHoras = idadeDias * 24
            idadeMinutos = idadeHoras * 60
            idadeSegundos = idadeMinutos * 60

            if (setMostrarAnos.isChecked) {
                tvExibirIdade.text = "Você é Corintiano há $idadeAnos anos."
            } else if (setMostrarMeses.isChecked) {
                tvExibirIdade.text = "Você é Corintiano há $idadeMeses meses."
            } else if (setMostrarDias.isChecked) {
                tvExibirIdade.text = "Você é Corintiano há $idadeDias dias."
            } else if (setMostrarHoras.isChecked) {
                tvExibirIdade.text = "Você é Corintiano há $idadeHoras horas."
            } else if (setMostrarMinutos.isChecked) {
                tvExibirIdade.text = "Você é Corintiano há $idadeMinutos minutos."
            } else if (setMostrarSegundos.isChecked) {
                tvExibirIdade.text = "Você é Corintiano há $idadeSegundos segundos."
            } else {
                tvExibirIdade.text = "Selecione uma opção de visualização de idade!"
            }

        }

        btnLimpar.setOnClickListener {
            enterAnoAtual.setText("")
            enterAnoNasc.setText("")
            radioGroup.clearCheck()
            tvExibirIdade.text = ""
        }

        btnSair.setOnClickListener {
            finishAndRemoveTask()
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}