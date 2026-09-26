package com.iter.app.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iter.app.data.model.SupportRole
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.components.buttons.ButtonSize
import com.iter.app.ui.components.buttons.CautionButton
import com.iter.app.ui.components.buttons.HelplineButton
import com.iter.app.ui.components.buttons.HelplineLink
import com.iter.app.ui.components.buttons.IconCircleButton
import com.iter.app.ui.components.buttons.PrimaryButton
import com.iter.app.ui.components.buttons.SecondaryButton
import com.iter.app.ui.components.buttons.SegmentedControl
import com.iter.app.ui.components.buttons.StreakChip
import com.iter.app.ui.components.buttons.SupportPulseButton
import com.iter.app.ui.components.buttons.TertiaryButton
import com.iter.app.ui.components.buttons.UtilityButton
import com.iter.app.ui.components.icons.IterIcons

/** Demo/QA screen: every button variant from the design spec, to check against it. */
@Composable
fun DesignGalleryScreen(onBack: () -> Unit) {
    var role by remember { mutableStateOf(SupportRole.Nudger) }
    ScreenColumn(title = "Design system", subtitle = "Every button from the spec, A to K.", onBack = onBack) {
        Group("A Primary: L / M / S") {
            PrimaryButton("Start today's check-in", {}, Modifier.fillMaxWidth(), size = ButtonSize.Large)
            PrimaryButton("Continue", {}, size = ButtonSize.Medium)
            PrimaryButton("Save", {}, size = ButtonSize.Small)
        }
        Group("B Secondary beside Primary") {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                SecondaryButton("Remind me later", {}, Modifier.weight(1f))
                PrimaryButton("Take it now", {}, Modifier.weight(1f))
            }
        }
        Group("C Tertiary") {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                TertiaryButton("Back", {})
                TertiaryButton("Why we ask this", {})
                TertiaryButton("Not today", {})
            }
        }
        Group("D Caution") { CautionButton("End shared access", {}) }
        Group("E Icon buttons") {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                IconCircleButton(IterIcons.Bell, "Notifications", {})
                IconCircleButton(IterIcons.Share, "Sharing", {})
                IconCircleButton(IterIcons.User, "Account", {})
            }
        }
        Group("F Utility (equal weight)") {
            UtilityButton("Survey frequency", IterIcons.Calendar, {}, Modifier.fillMaxWidth())
            UtilityButton("Medication context", IterIcons.Pill, {}, Modifier.fillMaxWidth())
            UtilityButton("Export PDF via email", IterIcons.Mail, {}, Modifier.fillMaxWidth())
        }
        Group("G Segmented control") {
            SegmentedControl(SupportRole.entries, role, { role = it }, { it.label }, Modifier.fillMaxWidth())
        }
        Group("H Support call (pulsing)") {
            SupportPulseButton("Talk it through — 30 sec", {}, icon = IterIcons.Phone)
        }
        Group("I Helpline (reserved terracotta)") {
            HelplineButton("Call 988 Suicide & Crisis Lifeline", {}, Modifier.fillMaxWidth(), icon = IterIcons.Phone)
            HelplineLink("In crisis right now? Call the 988 Lifeline", {})
        }
        Group("J Streak chip") { StreakChip("23-day streak") }
        Group("K Disabled") {
            PrimaryButton("Continue", {}, enabled = false)
            SecondaryButton("Skip for now", {}, enabled = false)
            TertiaryButton("Back", {}, enabled = false)
        }
    }
}

@Composable
private fun Group(title: String, content: @Composable () -> Unit) {
    SectionCard {
        Text(title, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) { content() }
    }
}
