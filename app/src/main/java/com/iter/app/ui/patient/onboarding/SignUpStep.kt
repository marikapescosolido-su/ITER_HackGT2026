package com.iter.app.ui.patient.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.components.buttons.ButtonSize
import com.iter.app.ui.components.buttons.PrimaryButton
import com.iter.app.ui.components.buttons.TertiaryButton
import com.iter.app.ui.theme.IterTheme

/** Sign up / log in. Demo only: no account is actually created and nothing leaves the phone. */
@Composable
fun SignUpStep(state: OnboardingState, onCreate: () -> Unit, onLogIn: () -> Unit) {
    Text(
        "Your treatment journey, one day at a time. A one-minute check-in each day helps your care team see how treatment is really going.",
        style = MaterialTheme.typography.bodyLarge,
    )
    SectionCard {
        OutlinedTextField(state.name, { state.name = it }, label = { Text("First name") }, singleLine = true, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            state.email, { state.email = it }, label = { Text("Email") }, singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
        )
        Text("Demo: no account is created and nothing is sent.", style = MaterialTheme.typography.labelSmall, color = IterTheme.chrome.charcoal)
    }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        PrimaryButton("Create account", onCreate, Modifier.fillMaxWidth(), size = ButtonSize.Large, enabled = state.canCreateAccount)
        TertiaryButton("Log in instead", onLogIn)
    }
}
