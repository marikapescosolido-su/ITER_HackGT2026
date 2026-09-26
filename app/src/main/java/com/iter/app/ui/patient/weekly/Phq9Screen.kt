package com.iter.app.ui.patient.weekly

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iter.app.data.DemoRepository
import com.iter.app.data.model.Phq9
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.SectionCard

/** Weekly PHQ-9. If item 9 is above 0, [onSubmitted] gets true and the safety screen opens. */
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Phq9Screen(onBack: () -> Unit, onSubmitted: (needsSafety: Boolean) -> Unit) {
    val answers = remember { mutableStateListOf<Int?>().apply { repeat(9) { add(null) } } }

    ScreenColumn(
        title = "Weekly questions",
        subtitle = "Over the last 2 weeks, how often have you been bothered by the following?",
        onBack = onBack,
    ) {
        Phq9.questions.forEachIndexed { i, question ->
            SectionCard {
                Text("${i + 1}. $question", style = MaterialTheme.typography.bodyLarge)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Phq9.options.forEachIndexed { value, option ->
                        FilterChip(
                            selected = answers[i] == value,
                            onClick = { answers[i] = value },
                            label = { Text(option) },
                        )
                    }
                }
            }
        }
        Button(
            onClick = {
                val phq9 = Phq9(DemoRepository.today, answers.map { it ?: 0 })
                DemoRepository.savePhq9(phq9)
                onSubmitted(phq9.needsSafetyFollowUp)
            },
            enabled = answers.none { it == null },
            modifier = Modifier.fillMaxWidth(),
        ) { Text("Save") }
    }
}
