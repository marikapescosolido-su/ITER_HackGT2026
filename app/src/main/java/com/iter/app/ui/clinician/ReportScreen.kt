package com.iter.app.ui.clinician

import androidx.compose.foundation.layout.Column
import com.iter.app.ui.components.icons.IterIcons
import com.iter.app.ui.components.buttons.UtilityButton
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.iter.app.data.DemoRepository
import com.iter.app.domain.ReportBuilder
import com.iter.app.domain.ReportInsights
import com.iter.app.ui.clinician.pdf.ReportPdfExporter
import com.iter.app.ui.components.ScreenColumn

/**
 * The weekly report. The doctor receives this as a PDF; this screen is the same content on the phone.
 * Order follows the README: visual overview first, AI-assisted summary, then the exact answers.
 */
@Composable
fun ReportScreen(onBack: () -> Unit, onOpenSettings: () -> Unit) {
    val repo = DemoRepository
    val context = LocalContext.current
    val report = ReportBuilder.build(
        patient = repo.patient,
        checkIns = repo.checkIns,
        phq9s = repo.phq9s,
        observations = repo.observations,
        windowDays = repo.reportSettings.windowDays,
        includeSupporter = repo.sharing.includeSupporterObservations,
    )
    val insights = ReportInsights.summarize(report)

    ScreenColumn(
        title = "Weekly report",
        subtitle = "${report.patient.name} · ${formatDate(report.start)} to ${formatDate(report.end)} · for ${report.patient.clinicianName}",
        onBack = onBack,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            UtilityButton("Survey frequency", IterIcons.Calendar, onOpenSettings, Modifier.fillMaxWidth())
            UtilityButton("Export PDF via email", IterIcons.Mail, {
                val ok = ReportPdfExporter.export(context, report, insights)
                if (!ok) Toast.makeText(context, "PDF export isn't built yet", Toast.LENGTH_SHORT).show()
            }, Modifier.fillMaxWidth())
        }

        if (!repo.sharing.shareWithClinician) {
            Text("${report.patient.name} has paused sharing with the clinician. This is a private preview.")
        }

        SafetyFlagsSection(report)
        OverviewSection(report)
        TreatmentSection(report)
        SummarySection(insights)
        TrendChartsSection(report)
        Phq9Section(report)
        SideEffectsSection(report)
        SupporterSection(report)
        MissingDataSection(report)
        NotesSection(report)
        if (repo.reportSettings.includeRawAnswers) RawAnswersSection(report)
        DisclaimerSection()
    }
}
