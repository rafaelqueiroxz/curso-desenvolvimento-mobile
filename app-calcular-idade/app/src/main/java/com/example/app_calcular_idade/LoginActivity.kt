package com.example.app_calcular_idade

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.biometric.BiometricManager


class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail : EditText
    private lateinit var etSenha : EditText
    private lateinit var btnEntrar : Button
    private lateinit var btnBiometria : Button

    private val tipoAutenticacao = BIOMETRIC_STRONG

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        etEmail = findViewById<EditText>(R.id.etEmail)
        etSenha = findViewById<EditText>(R.id.etSenha)
        btnEntrar = findViewById<Button>(R.id.btnEntrar)
        btnBiometria = findViewById<Button>(R.id.btnBiometria)



        btnEntrar.setOnClickListener {

            val email = etEmail.text.toString().trim()
            val senha = etSenha.text.toString().trim()

            if (email == "emailcerto@email.com" && senha == "1234") {

                realizarLogin()

            } else {

                Toast.makeText(this, "Email ou senha inválidos!",
                    Toast.LENGTH_SHORT).show()

            }

        }

        btnBiometria.setOnClickListener {
            verificarDisponibilidadeEAutenticar()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun verificarDisponibilidadeEAutenticar() {

        when (BiometricManager.from(this).canAuthenticate(tipoAutenticacao)) {

            BiometricManager.BIOMETRIC_SUCCESS -> exibirPromptBiometrico()

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                mostrarMensagem("Cadastre uma biometria nas configurações do seu aparelho.")

            else -> mostrarMensagem("Biometria indisponível neste aparelho.")
        }
    }

    private fun exibirPromptBiometrico() {

        val executor = ContextCompat.getMainExecutor(this)

        val callback = object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                realizarLogin()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                val canceladoPeloUsuario =
                    errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON ||
                            errorCode == BiometricPrompt.ERROR_USER_CANCELED

                if (!canceladoPeloUsuario) mostrarMensagem(errString.toString())
            }

        }

        val informacoesDoPrompt = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Confirme a sua identidade Kchorro")
            .setSubtitle("Use a sua biometria para entrar Curintia")
            .setNegativeButtonText("Cancelar")
            .setAllowedAuthenticators(tipoAutenticacao)
            .build()

        BiometricPrompt(this, executor, callback).authenticate(informacoesDoPrompt)

    }

    private fun mostrarMensagem(texto : String) {
        Toast.makeText(this, texto, Toast.LENGTH_LONG).show()
    }

    private fun realizarLogin() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}