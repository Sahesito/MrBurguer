package com.sahe.mrburguer.activity.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.sahe.mrburguer.activity.dashboard.MainActivity

class AuthActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val initialMode = intent.getStringExtra("AUTH_MODE") ?: "signin"

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                AuthScreenWithViewModel(
                    viewModel = viewModel,
                    initialMode = if (initialMode == "signup") AuthMode.SignUp else AuthMode.SignIn,
                    onAuthSuccess = {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                )
            }
        }
    }
}

@Composable
fun AuthScreenWithViewModel(
    viewModel: AuthViewModel,
    initialMode: AuthMode = AuthMode.SignIn,
    onAuthSuccess: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    var currentMode by remember { mutableStateOf(initialMode) }

    LaunchedEffect(state.user) {
        if (state.user != null) {
            Toast.makeText(
                context,
                "¡Welcome!",
                Toast.LENGTH_SHORT
            ).show()
            onAuthSuccess()
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    AuthScreen(
        mode = currentMode,
        onPrimary = { email, password ->
            when (currentMode) {
                AuthMode.SignIn -> viewModel.signIn(email, password)
                AuthMode.SignUp -> viewModel.signUp(email, password)
            }
        },
        onForgotPassword = {
            Toast.makeText(
                context,
                "Coming soon...",
                Toast.LENGTH_SHORT
            ).show()
        },
        onSwitch = {
            currentMode = if (currentMode == AuthMode.SignIn) {
                AuthMode.SignUp
            } else {
                AuthMode.SignIn
            }
        }
    )
}