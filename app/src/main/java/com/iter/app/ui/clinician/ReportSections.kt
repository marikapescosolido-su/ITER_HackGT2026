package com.iter.app.ui.clinician

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iter.app.data.model.DataSource
import com.iter.app.data.model.Question
import com.iter.app.domain.WeeklyReport
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.components.StatTile

// Text sections of the report. Charts are in ReportCharts.kt.

@Composable
fun SafetyFlagsSection(report: WeeklyReport) {
    if (report.safetyFlags.isEmpty()) return
    SectionCard(title = "Needs follow-up", source = DataSource.Patient, containerColor = MaterialTheme.colorScheme.errorContainer) {
        report.safetyFlags.forEach { Text(it) }
    }
}

@Composable
fun OverviewSection(report: WeeklyReport) {
    val latestPhq = report.phq9s.lastOrNull()
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        StatTile("Check-ins", "${report.completedDays}/${report.days.size}", "days completed", Modifier.weight(1f))
        StatTile("Doses taken", "${report.adherencePercent}%", "self-reported", Modifier.weight(1f))
        StatTile("PHQ-9", latestPhq?.total?.toString() ?: "–", latestPhq?.severity, Modifier.weight(1f))
    }
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        listOf(Question.Mood, Question.Anxiety, Question.Energy).forEach { q ->
            StatTile(
                q.label,
                formatAverage(report.average(q)),
                "prev ${formatAverage(report.previousAverage(q))}",
                Modifier.weight(1f),
            )
        }
    }
}

@Composable
fun TreatmentSection(report: WeeklyReport) {
    val p = report.patient
    SectionCard(title = "Treatment", source = DataSource.Patient) {
        p.medications.forEach { m ->
            Text("${m.name} ${m.dose} · ${m.purpose}${if (m.isMonitored) " (monitored)" else ""}")
        }
        report.doseChangesInPeriod.forEach {
            Text("Dose change ${formatDate(it.date)}: ${it.medication} ${it.from} → ${it.to}", style = MaterialTheme.typography.titleSmall)
        }
        Text("Baseline: usually sleeps ${p.baseline.typicalSleepHours} h, typical mood ${p.baseline.typicalMood}/10", style = MaterialTheme.typography.bodySmall)
        Text("Previous: ${p.baseline.previousMedications.joinToString()}", style = MaterialTheme.typography.bodySmall)
        Text("Family history: ${p.baseline.familyHistory.joinToString()}", style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun SummarySection(insights: List<String>) {
    SectionCard(title = "Summary", source = DataSource.Ai) {
        insights.forEach { Text("• $it") }
        Text(
            "Observations, not diagnoses. Check them against the answers below.",
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@Composable
fun SideEffectsSection(report: WeeklyReport) {
    SectionCard(title = "Side effects reported", source = DataSource.Patient) {
        if (report.sideEffectCounts.isEmpty()) Text("None reported.")
        report.sideEffectCounts.forEach { (effect, count) ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(effect.label)
                Text("$count of ${report.completedDays} days")
            }
        }
    }
}

@Composable
fun SupporterSection(report: WeeklyReport) {
    val latest = report.observations.lastOrNull() ?: return
    SectionCard(title = "${latest.supporterName}'s view (${formatDate(latest.date)})", source = DataSource.Supporter) {
        Text("Mood ${latest.mood}/10 · Energy ${latest.energy}/10 · Sleep ${latest.sleep}/10 · Social ${latest.social}/10")
        if (latest.note.isNotBlank()) Text("“${latest.note}”")
        Text(
            "Patient's own average mood this period: ${formatAverage(report.average(Question.Mood))}/10",
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
fun MissingDataSection(report: WeeklyReport) {
    if (report.missingDays.isEmpty()) return
    SectionCard(title = "Missing days") {
        Text(report.missingDays.joinToString { formatDate(it) })
        Text("No check-in on these days. Shown as gaps in charts; not counted as good or bad.", style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun NotesSection(report: WeeklyReport) {
    if (report.notes.isEmpty()) return
    SectionCard(title = "Patient notes", source = DataSource.Patient) {
        report.notes.forEach { (date, note) -> Text("${formatDate(date)}: “$note”") }
    }
}

@Composable
fun RawAnswersSection(report: WeeklyReport) {
    SectionCard(title = "All answers", source = DataSource.Patient) {
        report.checkIns.forEach { c ->
            val scores = c.scores.entries.joinToString { "${it.key.label} ${it.value}" }
            val meds = if (c.tookMedication) "dose taken" else "dose missed"
            val effects = c.sideEffects.joinToString { it.label }.ifEmpty { "no side effects" }
            Text("${formatDate(c.date)}: $scores · sleep ${c.sleepHours} h · $meds · $effects", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun DisclaimerSection() {
    Text(
        "ITER organizes patient-reported information for clinical review. It does not diagnose or recommend medication. " +
            "Summaries may be incomplete; correlation does not show that a medication caused a change.",
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}
