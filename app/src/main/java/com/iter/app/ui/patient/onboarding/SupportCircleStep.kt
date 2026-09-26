package com.iter.app.ui.patient.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.components.buttons.PrimaryButton
import com.iter.app.ui.components.buttons.SecondaryButton
import com.iter.app.ui.theme.IterTheme

/** The only optional onboarding step, so the only one with "Skip for now". */
@Composable
fun SupportCircleStep(state: OnboardingState, onInvite: () -> Unit, onSkip: () -> Unit) {
    Text("Would you like someone you trust to support you along the way?", style = MaterialTheme.typography.bodyLarge)
    Text(
        "They'll only see what you choose. You can change or end their access any time.",
        style = MaterialTheme.typography.bodyMedium,
        color = IterTheme.chrome.charcoal,
    )
    SectionCard {
        OutlinedTextField(state.inviteName, { state.inviteName = it }, label = { Text("Their name") }, singleLine = true, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(state.inviteRelation, { state.inviteRelation = it }, label = { Text("Relationship (e.g. partner)") }, singleLine = true, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(state.inviteContact, { state.inviteContact = it }, label = { Text("Phone or email") }, singleLine = true, modifier = Modifier.fillMaxWidth())
    }
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        SecondaryButton("Skip for now", onSkip, Modifier.weight(1f))
        PrimaryButton("Invite someone", onInvite, Modifier.weight(1f), enabled = state.canInvite)
    }
}
