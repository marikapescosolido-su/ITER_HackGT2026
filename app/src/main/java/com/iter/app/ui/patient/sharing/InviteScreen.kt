package com.iter.app.ui.patient.sharing

import androidx.compose.foundation.layout.fillMaxWidth
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
import com.iter.app.data.model.SupportRole
import com.iter.app.data.model.Supporter
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.components.buttons.PrimaryButton
import com.iter.app.ui.components.buttons.SegmentedControl
import com.iter.app.ui.components.icons.IterIcons
import com.iter.app.ui.theme.IterTheme

/** Wireflow "Invite Partner/Family" + "Assign Role": Primary "Send invite". Demo: nothing is sent. */
@Composable
fun InviteScreen(onBack: () -> Unit, onSent: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var relation by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var role by remember { mutableStateOf(SupportRole.Viewer) }

    ScreenColumn(title = "Invite someone", subtitle = "They'll get a link to a linked supporter account.", onBack = onBack) {
        SectionCard {
            OutlinedTextField(name, { name = it }, label = { Text("Their name") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(relation, { relation = it }, label = { Text("Relationship") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(contact, { contact = it }, label = { Text("Phone or email") }, singleLine = true, modifier = Modifier.fillMaxWidth())
        }
        SectionCard(title = "Assign role") {
            SegmentedControl(SupportRole.entries, role, { role = it }, { it.label }, Modifier.fillMaxWidth())
            Text(role.description, style = MaterialTheme.typography.bodySmall, color = IterTheme.chrome.charcoal)
        }
        PrimaryButton(
            "Send invite",
            {
                DemoRepository.addSupporter(Supporter(name.trim(), relation.trim().ifEmpty { "Supporter" }, role))
                onSent()
            },
            Modifier.fillMaxWidth(),
            enabled = name.isNotBlank() && contact.isNotBlank(),
            icon = IterIcons.Send,
        )
    }
}
