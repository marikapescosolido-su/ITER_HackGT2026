package com.iter.app.ui.patient.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.iter.app.data.DemoRepository
import com.iter.app.data.model.DoseRecord
import com.iter.app.domain.MedicationSupply
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.components.buttons.ChoiceChip
import com.iter.app.ui.components.buttons.TertiaryButton
import com.iter.app.ui.theme.Brand
import com.iter.app.ui.theme.IterTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

/** Each medication with an estimate of how many days of pills are left. */
@Composable
fun MedicationsCard() {
    val repo = DemoRepository
    SectionCard(title = "Your medications") {
        repo.patient.medications.forEach { m ->
            val days = MedicationSupply.daysLeft(m, repo.doseLog, repo.today)
            Column {
                Text("${m.name} ${m.dose}", style = MaterialTheme.typography.titleSmall)
                val supply = when {
                    days == null -> "Pill count not added yet"
                    days == 0 -> "You may have run out. Ask ${repo.patient.clinicianName} or your pharmacy for a refill."
                    MedicationSupply.isLow(days) -> "About $days days of pills left. Time to ask for a refill."
                    else -> "About $days days of pills left"
                }
                Text(supply, style = MaterialTheme.typography.bodySmall, color = IterTheme.chrome.charcoal)
            }
        }
    }
}

/** Search your medications, then see which days each one was taken. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MedicationCalendarCard() {
    val repo = DemoRepository
    val p = repo.patient
    var query by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf(p.monitoredMedication.name) }
    var month by remember { mutableStateOf(YearMonth.from(repo.today)) }

    val matches = p.medications.filter { it.name.contains(query.trim(), ignoreCase = true) }
    val pastMatches = p.baseline.previousMedications.filter { query.isNotBlank() && it.contains(query.trim(), ignoreCase = true) }

    SectionCard(title = "Medication calendar") {
        OutlinedTextField(
            query, { query = it },
            label = { Text("Search your medications") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            matches.forEach { m -> ChoiceChip(m.name, selected == m.name, { selected = m.name }) }
        }
        pastMatches.forEach {
            Text("Past: $it. Taken before ITER, so there's no daily record.", style = MaterialTheme.typography.bodySmall)
        }
        if (matches.isEmpty() && pastMatches.isEmpty()) {
            Text("No medication matches “${query.trim()}”.", style = MaterialTheme.typography.bodySmall)
        }

        val records = repo.doseLog.filter { it.medication == selected }
        Text(selected, style = MaterialTheme.typography.titleMedium)
        MonthHeader(month, onPrev = { month = month.minusMonths(1) }, onNext = { month = month.plusMonths(1) }, canGoNext = month < YearMonth.from(repo.today))
        MonthGrid(month, records, repo.today)
        Legend()
    }
}

@Composable
private fun MonthHeader(month: YearMonth, onPrev: () -> Unit, onNext: () -> Unit, canGoNext: Boolean) {
    val locale = currentLocale()
    fun shortMonth(m: YearMonth) = m.month.getDisplayName(TextStyle.SHORT, locale)
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        TertiaryButton("‹ ${shortMonth(month.minusMonths(1))}", onPrev)
        Text(
            "${month.month.getDisplayName(TextStyle.FULL, locale)} ${month.year}",
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
        )
        TertiaryButton("${shortMonth(month.plusMonths(1))} ›", onNext, enabled = canGoNext)
    }
}

/** Read through Compose so month and weekday names update if the phone's language changes. */
@Composable
private fun currentLocale(): Locale = LocalConfiguration.current.locales[0]

@Composable
private fun MonthGrid(month: YearMonth, records: List<DoseRecord>, today: LocalDate) {
    val byDate = records.associateBy { it.date }
    val leadingBlanks = month.atDay(1).dayOfWeek.value - DayOfWeek.MONDAY.value
    val locale = currentLocale()
    val cells: List<LocalDate?> = List(leadingBlanks) { null } + (1..month.lengthOfMonth()).map { month.atDay(it) }

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(Modifier.fillMaxWidth()) {
            DayOfWeek.entries.forEach {
                Text(
                    it.getDisplayName(TextStyle.NARROW, locale),
                    style = MaterialTheme.typography.labelSmall,
                    color = IterTheme.chrome.charcoal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                )
            }
        }
        cells.chunked(7).forEach { week ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                week.forEach { date ->
                    Box(Modifier.weight(1f).aspectRatio(1f), contentAlignment = Alignment.Center) {
                        if (date != null) DayCell(date, byDate[date], isFuture = date.isAfter(today))
                    }
                }
                repeat(7 - week.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun DayCell(date: LocalDate, record: DoseRecord?, isFuture: Boolean) {
    val chrome = IterTheme.chrome
    val (fill, border, text) = when {
        isFuture -> Triple(Color.Transparent, Color.Transparent, chrome.charcoal.copy(alpha = 0.35f))
        record?.taken == true -> Triple(Brand.Sage, Color.Transparent, Brand.OnSage)
        record?.taken == false -> Triple(Color.Transparent, chrome.charcoal.copy(alpha = 0.6f), chrome.charcoal)
        else -> Triple(Color.Transparent, Color.Transparent, chrome.charcoal.copy(alpha = 0.6f))
    }
    Box(
        Modifier.fillMaxWidth().aspectRatio(1f).padding(2.dp).background(fill, CircleShape).border(1.dp, border, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text("${date.dayOfMonth}", style = MaterialTheme.typography.labelMedium, color = text)
    }
}

@Composable
private fun Legend() {
    val chrome = IterTheme.chrome
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
        LegendItem("Taken", fill = Brand.Sage, border = Color.Transparent)
        LegendItem("Not taken", fill = Color.Transparent, border = chrome.charcoal.copy(alpha = 0.6f))
        LegendItem("No record", fill = Color.Transparent, border = chrome.hairline)
    }
}

@Composable
private fun LegendItem(label: String, fill: Color, border: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        Box(Modifier.size(12.dp).background(fill, CircleShape).border(1.dp, border, CircleShape))
        Text(label, style = MaterialTheme.typography.labelSmall, color = IterTheme.chrome.charcoal)
    }
}
