package com.example.app_calcular_idade

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor


class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail : EditText
    private lateinit var etSenha : EditText
    private lateinit var btnEntrar : Button
    private lateinit var btnEntrarBiometria : Button

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        etEmail = findViewById<EditText>(R.id.etEmail)
        etSenha = findViewById<EditText>(R.id.etSenha)
        btnEntrar = findViewById<Button>(R.id.btnEntrar)
        btnEntrarBiometria = findViewById<Button>(R.id.btnEntrarBiometria)

        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(applicationContext, "Erro: $errString", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(applicationContext, "Sucesso!", Toast.LENGTH_SHORT).show()

                // Abre a próxima tela
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(applicationContext, "Digital incorreta", Toast.LENGTH_SHORT).show()
            }
        })

        // Configurar a janela que vai subir na tela
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Login Biométrico")
            .setSubtitle("Use a sua digital ou rosto")
            .setNegativeButtonText("Usar senha")
            .build()

        // =========================================================================
        // SUBSTITUA A PARTE DO BIOMETRICMANAGER E DO CLIQUE POR ESTA:
        // =========================================================================

        // 1. Vamos testar o status da biometria imprimindo nos logs
        val biometricManager = BiometricManager.from(this)
        val autenticacaoStatus = biometricManager.canAuthenticate(BIOMETRIC_STRONG)

        android.util.Log.d("BIOMETRIA_TESTE", "Status do sensor: $autenticacaoStatus")

        // Forçar o botão a ficar visível para conseguirmos testar o clique
        btnEntrarBiometria.visibility = View.VISIBLE

        // 2. Novo clique do botão com logs e proteção contra erros
        btnEntrarBiometria.setOnClickListener {
            android.util.Log.d("BIOMETRIA_TESTE", "Botão clicado! Tentando abrir a janela...")

            try {
                // Dispara o prompt passando as configurações
                biometricPrompt.authenticate(promptInfo)
            } catch (e: Exception) {
                android.util.Log.e("BIOMETRIA_TESTE", "Erro ao chamar o authenticate: ${e.message}")
                Toast.makeText(this, "Falha interna: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        btnEntrar.setOnClickListener {

            val email = etEmail.text.toString().trim()
            val senha = etSenha.text.toString().trim()

            if (email == "emailcerto@email.com" && senha == "1234") {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                finish()

            } else {

                Toast.makeText(this, "Email ou senha inválidos!",
                    Toast.LENGTH_SHORT).show()

            }

        }

        btnEntrarBiometria.setOnClickListener {



        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    // Esta função tem de ficar FORA do onCreate, diretamente dentro da classe LoginActivity
    fun dispararBiometria(view: android.view.View) {
        android.util.Log.d("BIOMETRIA_TESTE", "O Android ativou a função com sucesso pelo XML!")
        try {
            biometricPrompt.authenticate(promptInfo)
        } catch (e: Exception) {
            Toast.makeText(this, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

}