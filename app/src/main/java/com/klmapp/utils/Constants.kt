package com.klmapp.utils

import androidx.compose.ui.unit.dp

object Constants {
    const val KEY_ID = "id"
    val COLLAPSED_TOP_BAR_HEIGHT = 80.dp
    val EXPANDED_TOP_BAR_HEIGHT = 250.dp
}
/*
@Composable
fun BookingDetailsScreen(navController: NavController) {
    val bookingViewModel: BookingDetailsViewModel = hiltViewModel()
    //val dataState by remember { bookingViewModel.dataState }
    val dataState by bookingViewModel.dataStateFlow.collectAsState()
    when (dataState) {
        is DataState.Loading -> {
            // Display loading indicator
            Text("Loading...")
        }

        is DataState.Success -> {
            val booking = (dataState as DataState.Success<Booking?>).data

            Scaffold(topBar = {
                TopAppBar(title = {
                    booking?.let {
                        Text(
                            text = it.name,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }, navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }, actions = {
                    IconButton(
                        onClick = {
                            // Handle another action
                        }
                    ) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                    }
                }, backgroundColor = AppColor, contentColor = BlackColor
                )
            }, content = {
                val pad = it
                ParallaxContent(booking)
            })*/
/* LazyColumn(
                 state = scrollState, modifier = Modifier.fillMaxSize(),
             ) {
                 item {
                     BookingDetailsHeader(title = booking?.name, imageUrl = booking?.url)
                 }
                 item {
                     BookingReference(booking = booking)
                     Row(
                         modifier = Modifier
                             .padding(start = 16.dp)
                             .fillMaxWidth(),
                         horizontalArrangement = Arrangement.Start,
                         verticalAlignment = Alignment.CenterVertically
                     ) {
                         AppText(
                             text = stringResource(id = R.string.totalDuration),
                             fontStyle = FontStyle.Normal,
                             fontWeight = null
                         )
                         booking?.let {
                             AppText(
                                 text = it.totalTripDuration,
                                 modifier = Modifier.padding(start = 8.dp)
                             )
                         }
                     }
                 }
                 items(timeLines!!) { timeLineItem ->
                     if (timeLineItem.haveTransferTime) {
                         TransferTimeLineItem(timeLineItem)
                     } else {
                         TimeLineListItem(timeLineItem)
                     }
                 }
             }*//*
*/
/*TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.bookings),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal
                )
            }, backgroundColor = AppColor, contentColor = BlackColor, elevation = 0.dp)*//*


        }

        is DataState.Error -> {
            // Handle the error
            Text("Error: ${(dataState as DataState.Error).exception.message}")
        }
    }


}*/
