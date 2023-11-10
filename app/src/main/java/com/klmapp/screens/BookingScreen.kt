package com.klmapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.klmapp.R
import com.klmapp.components.booking.BookingListItem
import com.klmapp.components.booking.BookingTabs
import com.klmapp.data.DataState
import com.klmapp.model.Booking
import com.klmapp.ui.theme.AppColor
import com.klmapp.ui.theme.BlackColor
import com.klmapp.ui.theme.TabsColor
import com.klmapp.viewmodel.BookingViewModel
import kotlinx.coroutines.launch

/**
 * Composable function for displaying the Booking Screen.
 *
 * @param onClick Callback function invoked when a booking item is clicked, providing the item's ID.
 */
@Composable
fun BookingScreen(onClick: (id: Int) -> Unit) {
    // Create state variables for managing the Scaffold and CoroutineScope
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(scaffoldState = scaffoldState, topBar = {
        TopAppBar(title = {
            Text(
                text = stringResource(id = R.string.bookings),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal
            )
        }, backgroundColor = AppColor, contentColor = BlackColor, elevation = 0.dp)
    }, floatingActionButton = {
        val msg = stringResource(id = R.string.fab_msg)
        val dismiss = stringResource(id = R.string.dismiss)
        FloatingActionButton(
            backgroundColor = TabsColor, contentColor = Color.White, onClick = {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = msg, actionLabel = dismiss
                    )
                }
            }, modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_16_dp))
        ) {
            Icon(
                imageVector = Icons.Default.Add, contentDescription = null
            )
        }
    }) {
        Box(modifier = Modifier.padding(it)) {
            BookingTabs(onClick = onClick)
        }
    }
}

/**
 * Composable function for displaying a list of bookings.
 *
 * @param onClick Callback function invoked when a booking item is clicked, providing the item's ID.
 */
@Composable
fun BookingList(onClick: (id: Int) -> Unit, isCurrentBooking: Boolean) {
    val bookingViewModel: BookingViewModel = hiltViewModel()
    val dataState by bookingViewModel.dataStateFlow.collectAsState()
    when (dataState) {
        is DataState.Loading -> {
            // Display loading indicator
            Text(stringResource(id = R.string.loading))
        }

        is DataState.Success -> {
            val bookings = (dataState as DataState.Success<List<Booking>>).data
            // Display the loaded data
            LazyColumn(content = {
                items(bookings) {

                    BookingListItem(booking = it, isCurrentBooking) { id ->
                        if (isCurrentBooking)
                            onClick.invoke(id)
                    }
                    Divider(color = Color.LightGray)
                }
            })

        }

        is DataState.Error -> {
            // Handle the error
            Text("Error: ${(dataState as DataState.Error).exception.message}")
        }
    }
}





