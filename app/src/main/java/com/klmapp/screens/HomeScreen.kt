package com.klmapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.klmapp.R
import com.klmapp.utils.AppTextSize
import com.klmapp.widget.AppText

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.dim_20_dp))
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        AppText(
            text = stringResource(id = R.string.welcome_msg),
            fontSize = AppTextSize.Large,
            textAlign = TextAlign.Center
        )
    }

}

@Preview(showBackground = true, widthDp = 360, heightDp = 400)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}