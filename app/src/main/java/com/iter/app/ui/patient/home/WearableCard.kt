package com.iter.app.ui.patient.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.iter.app.data.DemoRepository
import com.iter.app.data.model.SleepStages
import com.iter.app.ui.components.PanelShape
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.components.StatTile
import com.iter.app.ui.components.icons.IterIcons
import com.iter.app.ui.theme.Brand
import com.iter.app.ui.theme.IterTheme
import com.iter.app.ui.theme.Sora

/** The patient's connected smartwatch and what it measured, two readings per row. */
@Composable
fun WearableCard() {
    val watch = DemoRepository.wearable
    val chrome = IterTheme.chrome
    SectionCard(title = "Connected to your SmartWatch") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(IterIcons.Watch, contentDescription = null, tint = chrome.brandText, modifier = Modifier.size(20.dp))
            Text("${watch.deviceName} · Synced ${watch.lastSynced}", style = MaterialTheme.typography.bodySmall, color = chrome.charcoal)
        }
        watch.readings.chunked(2).forEach { pair ->
            Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                pair.forEach { StatTile(it.label, it.value, it.detail, Modifier.weight(1f).fillMaxHeight()) }
                if (pair.size == 1) Box(Modifier.weight(1f))
            }
        }
        SleepStagesTile(watch.sleep)
    }
}

/** Last night's sleep as one bar split by stage, with each stage's time underneath. */
@Composable
private fun SleepStagesTile(sleep: SleepStages) {
    val chrome = IterTheme.chrome
    val stages = listOf(
        Triple("Awake", sleep.awake, Brand.ChartSand),
        Triple("REM", sleep.rem, Brand.Sage),
        Triple("Light", sleep.light, Brand.Mist),
        Triple("Deep", sleep.deep, Brand.ChartSlate),
    )
    Column(
        Modifier
            .fillMaxWidth()
            .background(chrome.panel, PanelShape)
            .border(1.dp, chrome.hairline, PanelShape)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text("Sleep stages", style = MaterialTheme.typography.labelMedium, color = chrome.charcoal)
            Text(
                "${duration(sleep.asleep)} asleep",
                style = MaterialTheme.typography.headlineSmall.copy(fontFamily = Sora, fontWeight = FontWeight.SemiBold),
                color = chrome.ink,
            )
            Text("Last night", style = MaterialTheme.typography.labelSmall, color = chrome.charcoal)
        }
        Row(Modifier.fillMaxWidth().height(10.dp).clip(RoundedCornerShape(5.dp))) {
            stages.forEach { (_, minutes, color) ->
                Box(Modifier.weight(minutes.toFloat()).fillMaxHeight().background(color))
            }
        }
        stages.chunked(2).forEach { pair ->
            Row(Modifier.fillMaxWidth()) {
                pair.forEach { (name, minutes, color) -> StageLegend(name, minutes, color, Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun StageLegend(name: String, minutes: Int, color: Color, modifier: Modifier) {
    Row(modifier, horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(8.dp).background(color, CircleShape))
        Text("$name ${duration(minutes)}", style = MaterialTheme.typography.labelSmall, color = IterTheme.chrome.charcoal)
    }
}

private fun duration(minutes: Int) = if (minutes >= 60) "${minutes / 60}h ${minutes % 60}m" else "${minutes}m"
