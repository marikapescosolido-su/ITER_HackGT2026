package com.iter.app.ui.patient.safety

import com.iter.app.ui.components.icons.IterIcons
import com.iter.app.ui.components.buttons.TertiaryButton
import com.iter.app.ui.components.buttons.HelplineButton
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.iter.app.domain.Safety
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.SectionCard

/** Shown when answers suggest a safety concern. Calm wording; points to real people. */
@Composable
fun SafetyScreen(onDone: () -> Unit) {
    val context = LocalContext.current
    ScreenColumn(
        title = "You don't have to go through this alone",
        subtitle = "Thank you for answering honestly. Talking to someone can help right now.",
    ) {
        Safety.resources.forEach { resource ->
            SectionCard(title = resource.title) {
                Text(resource.detail)
                resource.phone?.let { phone ->
                    HelplineButton(
                        "Call $phone",
                        { context.startActivity(Intent(Intent.ACTION_DIAL, "tel:$phone".toUri())) },
                        Modifier.fillMaxWidth(),
                        icon = IterIcons.Phone,
                    )
                }
            }
        }
        Text(Safety.DISCLAIMER, style = MaterialTheme.typography.bodySmall)
        TertiaryButton("Back to home", onDone)
    }
}
