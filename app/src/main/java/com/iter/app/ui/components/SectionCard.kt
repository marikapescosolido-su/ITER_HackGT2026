package com.iter.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.iter.app.data.model.DataSource
import com.iter.app.ui.theme.IterTheme

val PanelShape = RoundedCornerShape(10.dp)

/** Panel: 1px hairline border instead of a shadow, 10dp radius (spec section 6). */
@Composable
fun Panel(
    modifier: Modifier = Modifier,
    color: Color = IterTheme.chrome.panel,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier
            .fillMaxWidth()
            .background(color, PanelShape)
            .border(1.dp, IterTheme.chrome.hairline, PanelShape)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        content = content,
    )
}

@Composable
fun SectionCard(
    title: String? = null,
    source: DataSource? = null,
    containerColor: Color = IterTheme.chrome.panel,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Panel(modifier, containerColor) {
        if (title != null || source != null) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (title != null) {
                    Text(title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                }
                if (source != null) SourceTag(source)
            }
        }
        content()
    }
}
