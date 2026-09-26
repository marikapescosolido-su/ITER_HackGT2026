package com.iter.app.ui.components.buttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.iter.app.ui.theme.Brand
import com.iter.app.ui.theme.IterTheme

private val SageTint10 = Brand.Sage.copy(alpha = 0.10f)
private val SageTint16 = Brand.Sage.copy(alpha = 0.16f)

/** A) The one emphasized action per screen. */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Medium,
    enabled: Boolean = true,
    icon: ImageVector? = null,
) = ButtonBase(
    text, onClick, modifier, enabled, sizeShape(size), size.minHeight, size.horizontalPadding, size.text,
    normal = ButtonColors(Brand.Sage, Brand.OnSage),
    hovered = ButtonColors(Brand.SageHover, Brand.OnSage),
    pressed = ButtonColors(Brand.SagePressed, Brand.OnSage),
    icon = icon,
)

/** B) Lower-emphasis option next to a primary button. Never the only button on a screen. */
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Medium,
    enabled: Boolean = true,
    icon: ImageVector? = null,
) = ButtonBase(
    text, onClick, modifier, enabled, sizeShape(size), size.minHeight, size.horizontalPadding, size.text,
    normal = ButtonColors(Color.Transparent, IterTheme.chrome.brandText, Brand.Sage),
    hovered = ButtonColors(SageTint10, IterTheme.chrome.brandText, Brand.Sage),
    pressed = ButtonColors(SageTint16, IterTheme.chrome.brandText, Brand.Sage),
    icon = icon,
)

/** D) Consequential but not urgent (End shared access, Remove editor). Deliberately not red. */
@Composable
fun CautionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Medium,
    enabled: Boolean = true,
) {
    val charcoal = IterTheme.chrome.charcoal // page-chrome color: adapts in dark mode per the spec
    ButtonBase(
        text, onClick, modifier, enabled, sizeShape(size), size.minHeight, size.horizontalPadding, size.text,
        normal = ButtonColors(Color.Transparent, charcoal, charcoal),
        hovered = ButtonColors(charcoal.copy(alpha = 0.06f), charcoal, charcoal),
        pressed = ButtonColors(charcoal.copy(alpha = 0.10f), charcoal, charcoal),
    )
}

/** I) The one reserved use of terracotta: crisis resources only. */
@Composable
fun HelplineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
) = ButtonBase(
    text, onClick, modifier, enabled = true, sizeShape(ButtonSize.Medium), ButtonSize.Medium.minHeight,
    ButtonSize.Medium.horizontalPadding, ButtonSize.Medium.text,
    normal = ButtonColors(Brand.Terracotta, Brand.Cream),
    hovered = ButtonColors(Brand.TerracottaHover, Brand.Cream),
    pressed = ButtonColors(Brand.TerracottaHover, Brand.Cream),
    icon = icon,
)
