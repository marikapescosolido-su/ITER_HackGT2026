package com.iter.app.ui.supporter

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.iter.app.data.DemoRepository
import com.iter.app.data.model.Nudge
import com.iter.app.domain.SupportMessages
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.SectionCard
import java.time.LocalDateTime

/** Nudges are limited to one per day so they never become pressure. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NudgeScreen(onBack: () -> Unit) {
    val repo = DemoRepository
    val alreadySent = repo.nudges.any { it.from == DEMO_SUPPORTER && it.sentAt.toLocalDate() == repo.today }
    var message by remember { mutableStateOf(SupportMessages.nudgeTemplates.first()) }
    var sent by remember { mutableStateOf(false) }

    ScreenColumn(title = "Send a nudge", subtitle = "A kind word, not a warning. ${repo.patient.name} can mute nudges any time.", onBack = onBack) {
        when {
            sent -> SectionCard { Text("Sent. ${repo.patient.name} will see it on their home screen.") }
            alreadySent -> SectionCard { Text("You've already sent a nudge today. One a day keeps it gentle.") }
            else -> {
                SectionCard(title = "Pick a message or write your own") {
                    SupportMessages.nudgeTemplates.forEach { template ->
                        FilterChip(selected = message == template, onClick = { message = template }, label = { Text(template) })
                    }
                    OutlinedTextField(value = message, onValueChange = { message = it }, modifier = Modifier.fillMaxWidth())
                }
                Button(
                    onClick = {
                        repo.sendNudge(Nudge(DEMO_SUPPORTER, message.trim(), LocalDateTime.now()))
                        sent = true
                    },
                    enabled = message.isNotBlank(),
                    modifier = Modifier.fillMaxWidth(),
                ) { Text("Send") }
                Text("Only your message is shared. Nothing about ${repo.patient.name}'s answers.", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
