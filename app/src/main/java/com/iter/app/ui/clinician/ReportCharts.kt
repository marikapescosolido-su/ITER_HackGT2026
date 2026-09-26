package com.iter.app.ui.clinician

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.iter.app.data.model.DataSource
import com.iter.app.data.model.Question
import com.iter.app.domain.WeeklyReport
import com.iter.app.ui.components.ChartSeries
import com.iter.app.ui.components.LineChart
import com.iter.app.ui.components.SectionCard

@Composable
fun TrendChartsSection(report: WeeklyReport) {
    val colors = MaterialTheme.colorScheme
    val doseChange = report.doseChangesInPeriod.firstOrNull()
    val markerIndex = doseChange?.let { report.days.indexOf(it.date) }?.takeIf { it >= 0 }
    val start = formatDate(report.start)
    val end = formatDate(report.end)

    SectionCard(title = "Low mood, tension and exhaustion (0-10, higher = more)", source = DataSource.Patient) {
        LineChart(
            series = listOf(
                ChartSeries("Low mood", report.daily(Question.Mood), colors.primary),
                ChartSeries("Tension", report.daily(Question.Tension), colors.tertiary),
                ChartSeries("Exhaustion", report.daily(Question.Exhaustion), colors.secondary),
            ),
            yMax = 10f,
            startLabel = start,
            endLabel = end,
            markerIndex = markerIndex,
            markerLabel = doseChange?.let { "dose ${it.to}" },
        )
    }
    SectionCard(title = "Sleep (hours, and disruption 0-10)", source = DataSource.Patient) {
        LineChart(
            series = listOf(
                ChartSeries("Hours", report.dailySleepHours(), colors.secondary),
                ChartSeries("Disruption", report.daily(Question.Sleep), colors.primary),
            ),
            yMax = 12f,
            startLabel = start,
            endLabel = end,
        )
    }
}

@Composable
fun Phq9Section(report: WeeklyReport) {
    if (report.phq9s.size < 2) return
    SectionCard(title = "PHQ-9 by week (0-27, lower is better)", source = DataSource.Patient) {
        LineChart(
            series = listOf(ChartSeries("PHQ-9", report.phq9s.map { it.total.toFloat() }, MaterialTheme.colorScheme.primary)),
            yMax = 27f,
            startLabel = formatDate(report.phq9s.first().date),
            endLabel = formatDate(report.phq9s.last().date),
        )
    }
}
