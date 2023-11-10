package com.klmapp.components.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.klmapp.R
import com.klmapp.model.Booking
import com.klmapp.model.BookingTimeLine
import com.klmapp.utils.AppTextSize
import com.klmapp.widget.AppCard
import com.klmapp.widget.AppText

/**
 * Composable function for displaying booking reference details.
 *
 * @param booking The Booking object containing reference and trip information.
 */
@Composable
fun BookingReference(booking: Booking?) {
    // AppCard is a custom widget providing a card-like container with padding
    AppCard {
        Column(Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AppText(
                    text = stringResource(id = R.string.booking_reference),
                    fontSize = AppTextSize.Small
                )
                booking?.bookingReferenceNumber?.let {
                    RoundedTextWithBackground(
                        text = it.uppercase(), backgroundColor = Color.Blue, textColor = Color.White
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                AppText(
                    text = stringResource(id = R.string.departure),
                    modifier = Modifier.padding(end = 8.dp)
                )
                booking?.departureTime?.let {
                    AppText(
                        text = it, fontWeight = FontWeight.Normal
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.baseline_airplane_ticket_24),
                    contentDescription = "",
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                booking?.tripType?.let { AppText(text = it) }
            }

        }

    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 120)
@Composable
fun BookingReferencePreview() {
    // Create a sample Booking for preview
    val sampleBooking = Booking(
        id = 1,
        name = "Sample Destination",
        date = "2023-11-10",
        noOfPersons = 2,
        url = "https://example.com/image.jpg",
        tripType = "Round Trip",
        departureTime = "10:00 AM",
        bookingReferenceNumber = "ABC123",
        totalTripDuration = "3 days",
        bookingTimeLine = listOf(
            BookingTimeLine(
                date = "2023-11-10",
                time = "10:00 AM",
                airportName = "Sample Airport",
                isTimeUpdated = false,
                updatedTime = "",
                haveTransferTime = true,
                transferTime = "1 hour"
            )
        )
    )

    // Display the BookingReference composable with the sample data
    BookingReference(booking = sampleBooking)
}

