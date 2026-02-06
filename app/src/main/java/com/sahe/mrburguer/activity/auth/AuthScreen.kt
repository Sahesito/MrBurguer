package com.sahe.mrburguer.activity.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sahe.mrburguer.R

private val PrimaryRed = Color(0xffe53935)
enum class AuthMode { SignIn, SignUp }

@Composable
fun AuthScreen(
    mode: AuthMode = AuthMode.SignIn,
    onPrimary:(String, String) -> Unit = { _, _ -> },
    onForgotPassword: () -> Unit = {},
    onSwitch: () -> Unit = {},
) {
    val isSignUp = mode == AuthMode.SignUp

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    val titleText = if(isSignUp) "Sign up" else "Sign in"
    val primaryText = if(isSignUp) "Create account" else "Sign in"
    val bottomPrompt = if(isSignUp) "Do you have an account? " else "Don't have an account? "
    val bottomLink = if(isSignUp) "Sign in" else "Sign up"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.darkBrown))
    ) {
        Image(
            painter = painterResource(id = R.drawable.intro_pic),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(40.dp))

            Image(
                painter = painterResource(R.drawable.pizza),
                contentDescription = null,
                modifier = Modifier
                    .height(120.dp)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = titleText,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 12.dp),
                textAlign = TextAlign.Start
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                singleLine = true,
                label = { Text("Email") },
                leadingIcon = { Icon(painterResource(R.drawable.phone_storake), null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White.copy(alpha = 0.9f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = PrimaryRed,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = PrimaryRed
                )
            )
            Spacer(Modifier.height(12.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                singleLine = true,
                label = { Text("Password") },
                leadingIcon = { Icon(painterResource(R.drawable.lock), null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    TextButton(onClick = { showPassword = !showPassword }) {
                        Text(if (showPassword) "Hide" else "Show")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White.copy(alpha = 0.9f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = PrimaryRed,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = PrimaryRed
                )
            )

            if (isSignUp) {
                Spacer(Modifier.height(12.dp))
                TextField(
                    value = confirm,
                    onValueChange = { confirm = it },
                    singleLine = true,
                    label = { Text("Repeat password") },
                    leadingIcon = { Icon(painterResource(R.drawable.lock), null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.9f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = PrimaryRed,
                        unfocusedIndicatorColor = Color.Gray,
                        cursorColor = PrimaryRed
                    )
                )
            }

            if (!isSignUp) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 8.dp)
                ) {
                    TextButton(
                        onClick = onForgotPassword,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Text("Forgot password", color = Color.White)
                    }
                }
            }

            Spacer(Modifier.height(if (isSignUp) 32.dp else 24.dp))

            Button(
                onClick = { onPrimary(email, password) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryRed,
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(primaryText, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.weight(1f))
            Spacer(Modifier.height(16.dp))

            val annotated = buildAnnotatedString {
                withStyle(SpanStyle(color = Color.White)) {
                    append(bottomPrompt)
                }
                pushStringAnnotation("switch", "switch")
                withStyle(SpanStyle(color = PrimaryRed, fontWeight = FontWeight.Bold)) {
                    append(bottomLink)
                }
                pop()
            }
            ClickableText(
                text = annotated,
                onClick = { off ->
                    annotated.getStringAnnotations("switch", off, off)
                        .firstOrNull()?.let { onSwitch() }
                },
                modifier = Modifier
                    .padding(vertical = 18.dp)
                    .navigationBarsPadding()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    AuthScreen()
}