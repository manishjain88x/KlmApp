package com.klmapp.components.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.klmapp.model.BookingTimeLine
import com.klmapp.ui.theme.TimeTextColor
import com.klmapp.widget.AppText

@Composable
fun TimeLineListItem(
    timeLine: BookingTimeLine
) {
    Card(
        elevation = 8.dp, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AppText(text = timeLine.date)
            Row {
                if (timeLine.isTimeUpdated) {
                    AppText(
                        text = timeLine.time,
                        style = LocalTextStyle.current.copy(textDecoration = TextDecoration.LineThrough)
                    )
                    AppText(
                        text = timeLine.updatedTime,
                        color = TimeTextColor,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                } else {
                    AppText(
                        text = timeLine.time,
                    )
                }

            }
            AppText(text = timeLine.airportName)

        }

    }
}