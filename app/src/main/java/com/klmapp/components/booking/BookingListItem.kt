package com.klmapp.components.booking

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.klmapp.R
import com.klmapp.model.Booking
import com.klmapp.model.BookingTimeLine
import com.klmapp.widget.AppText

/**
 * Composable function for displaying a single booking item.
 *
 * @param booking The booking data to be displayed.
 * @param isCurrentBooking flag for managing views for Current and Past Booking.
 * @param onClick Callback function to be triggered when the item is clicked.
 */

@Composable
fun BookingListItem(booking: Booking, isCurrentBooking: Boolean, onClick: (id: Int) -> Unit) {
    // Load the booking image asynchronously using Coil library
    val painter = rememberAsyncImagePainter(model = booking.url)

    // Row container for the entire booking item
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                // Trigger the provided onClick callback when the item is clicked
                onClick(booking.id)
            }) {
        // Image displaying the booking image
        Image(
            painter = painter, contentDescription = "", Modifier.size(
                height = dimensionResource(id = R.dimen.img_height), width = dimensionResource(
                    id = R.dimen.img_width
                )
            ), contentScale = ContentScale.FillHeight
        )
        // Column for booking details
        Column(
            Modifier
                .padding(dimensionResource(id = R.dimen.dim_8_dp))
                .fillMaxSize()
                .weight(0.8f)
        ) {
            AppText(text = booking.name, fontWeight = FontWeight.ExtraBold)
            AppText(
                text = booking.date, fontWeight = FontWeight.Normal
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dim_8_dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_person_24),
                    contentDescription = "",
                    Modifier.size(dimensionResource(id = R.dimen.dim_24_dp))

                )
                AppText(text = "${booking.noOfPersons}")

            }
        }
        if (isCurrentBooking) {
            Image(
                painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                contentDescription = "",
                Modifier.size(dimensionResource(id = R.dimen.dim_24_dp)),
                alignment = Alignment.CenterEnd
            )
        }

    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 150)
@Composable
fun BookingListItemPreview(
    booking: Booking = Booking(
        id = 1,
        name = "Pau - Lisbon",
        date = "From Oct 21 to Nov 18, 2023",
        noOfPersons = 2,
        url = "https://example.com/image.jpg",
        tripType = "Sample Trip",
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
    ), onClick: (id: Int) -> Unit = {}
) {
    BookingListItem(booking = booking, true, onClick = onClick)
}



