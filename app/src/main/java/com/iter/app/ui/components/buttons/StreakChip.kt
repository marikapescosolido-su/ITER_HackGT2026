package com.iter.app.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.iter.app.ui.theme.Brand
import com.iter.app.ui.theme.ButtonText

/** J) Status badge, not interactive. */
@Composable
fun StreakChip(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = ButtonText.Small.copy(fontWeight = FontWeight.SemiBold),
        color = Color(0xFF2E2E2E),
        modifier = modifier
            .background(Brand.Mist, RoundedCornerShape(999.dp))
            .padding(horizontal = 14.dp, vertical = 7.dp),
    )
}
