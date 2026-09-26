package com.team.checkin.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.team.checkin.data.DemoRepository

// Owner: Person B
@Composable
fun HomeScreen(
    onStartCheckIn: () -> Unit,
    onOpenPartner: () -> Unit,
    onOpenReport: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "Hi ${DemoRepository.patient.name}",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Text("${DemoRepository.streak}-day streak", style = MaterialTheme.typography.titleMedium)
        Button(onClick = onStartCheckIn, modifier = Modifier.fillMaxWidth()) { Text("Start today's check-in") }
        // Demo shortcuts: in the pitch these are "other people's phones".
        OutlinedButton(onClick = onOpenPartner, modifier = Modifier.fillMaxWidth()) { Text("Partner view") }
        OutlinedButton(onClick = onOpenReport, modifier = Modifier.fillMaxWidth()) { Text("Doctor weekly report") }
    }
}
