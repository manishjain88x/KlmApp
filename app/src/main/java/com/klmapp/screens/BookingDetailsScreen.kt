package com.klmapp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.klmapp.R
import com.klmapp.components.details.BookingReference
import com.klmapp.components.details.TimeLineListItem
import com.klmapp.data.DataState
import com.klmapp.model.Booking
import com.klmapp.model.BookingTimeLine
import com.klmapp.ui.theme.AppColor
import com.klmapp.ui.theme.BlackColor
import com.klmapp.ui.theme.DurationCardColorBG
import com.klmapp.utils.Constants.COLLAPSED_TOP_BAR_HEIGHT
import com.klmapp.utils.Constants.EXPANDED_TOP_BAR_HEIGHT
import com.klmapp.viewmodel.BookingDetailsViewModel
import com.klmapp.widget.AppText

/**
 * Composable function for displaying the details of a booking.
 *
 * @param navController NavController for handling navigation within the app.
 */
@Composable
fun BookingDetailsScreen(navController: NavController) {
    // Retrieve the BookingDetailsViewModel using Hilt
    val bookingViewModel: BookingDetailsViewModel = hiltViewModel()

    // Create state variables for managing LazyListState and collecting data state flow
    val listState = rememberLazyListState()
    val dataState by bookingViewModel.dataStateFlow.collectAsState()
    val overlapHeightPx = with(LocalDensity.current) {
        EXPANDED_TOP_BAR_HEIGHT.toPx() - COLLAPSED_TOP_BAR_HEIGHT.toPx()
    }
    val isCollapsed: Boolean by remember {
        derivedStateOf {
            val isFirstItemHidden = listState.firstVisibleItemScrollOffset > overlapHeightPx
            isFirstItemHidden || listState.firstVisibleItemIndex > 0
        }
    }
    when (dataState) {
        is DataState.Loading -> {
            // Display loading indicator
            Text(stringResource(id = R.string.loading))
        }

        is DataState.Success -> {
            val booking = (dataState as DataState.Success<Booking?>).data
            val imageUrl = booking?.url
            val title = booking?.name
            val timeLines = booking?.bookingTimeLine
            Box {
                CollapsedTopBar(
                    modifier = Modifier.zIndex(2f), isCollapsed = isCollapsed, navController, title
                )
                LazyColumn(state = listState) {
                    item { ExpandedTopBar(imageUrl.toString(), title.toString()) }
                    item {
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_16_dp)))
                        BookingReference(booking = booking)
                        Row(
                            modifier = Modifier
                                .padding(start = dimensionResource(id = R.dimen.dim_16_dp))
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
                                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dim_8_dp))
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
                        // Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

        }

        is DataState.Error -> {
            // Handle the error
            Text("Error: ${(dataState as DataState.Error).exception.message}")
        }
    }


}

/**
 * Composable function for displaying time line items with transfer times.
 *
 * @param timeLineItem BookingTimeLine item with transfer time information.
 */
@Composable
fun TransferTimeLineItem(timeLineItem: BookingTimeLine) {
    // Composable for items with transfer times
    Card(
        elevation = dimensionResource(id = R.dimen.dim_8_dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.dim_16_dp))
            .border(
                width = 1.dp,
                color = Color.Blue,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_8_dp))
            ),
        backgroundColor = DurationCardColorBG
    ) {
        Text(
            text = stringResource(id = R.string.transfer_time, timeLineItem.transferTime),
            modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_24_dp))
        )
    }

}

/**
 *
 * Composable function for displaying the collapsed top bar.
 *
 * @param modifier Modifier for customizing the appearance of the top bar.
 * @param isCollapsed Boolean indicating whether the top bar is collapsed.
 * @param navController NavController for handling navigation within the app.
 * @param title Title to be displayed in the top bar.
 */
@Composable
private fun CollapsedTopBar(
    modifier: Modifier = Modifier,
    isCollapsed: Boolean,
    navController: NavController,
    title: String?
) {
    val color: Color by animateColorAsState(
        if (isCollapsed) MaterialTheme.colors.background else Color.Transparent, label = ""
    )

    Box(
        modifier = modifier
            .background(color)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomStart
    ) {
        AnimatedVisibility(visible = isCollapsed) {
            TopAppBar(title = {
                Text(
                    text = title.toString(),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal
                )
            }, navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }, actions = {
                IconButton(onClick = {
                    // Handle another action
                }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }
            }, backgroundColor = AppColor, contentColor = BlackColor)
        }
    }
}

@Composable
private fun ExpandedTopBar(imageUrl: String, title: String) {
    val painter = rememberAsyncImagePainter(model = imageUrl)
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.primaryVariant)
            .fillMaxWidth()
            .height(EXPANDED_TOP_BAR_HEIGHT), contentAlignment = Alignment.BottomStart
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Text(
            modifier = Modifier.padding(16.dp),
            text = title,
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h6,
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 200)
@Composable
fun ExpandedTopBarPreview() {
    ExpandedTopBar(
        imageUrl = "https://example.com/image.jpg", // Replace with an actual URL
        title = "Sample Title"
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 56)
@Composable
fun CollapsedTopBarPreview() {
    val navController = rememberNavController()

    CollapsedTopBar(
        isCollapsed = true, navController = navController, title = "Sample Title"
    )
}


