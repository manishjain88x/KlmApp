package com.klmapp.widget

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.klmapp.utils.AppTextSize

// for font size enum class {}
@Composable
fun AppText(
    text: String,
    color: Color = Color.Black,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = FontWeight.Bold,
    fontSize: AppTextSize = AppTextSize.Medium,
    textAlign: TextAlign? = null
) {
    Text(
        text = text,
        fontSize = fontSize.value,
        modifier = modifier,
        fontStyle = fontStyle,
        color = color,
        style = style,
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}


