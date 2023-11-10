package com.klmapp.components.booking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.klmapp.R
import com.klmapp.screens.BookingList
import com.klmapp.ui.theme.AppColor
import com.klmapp.ui.theme.SelectionColor
import com.klmapp.ui.theme.TabsColor
import kotlinx.coroutines.launch

// Constants representing tabs
private const val CURRENT_TAB = "CURRENT"
private const val PAST_TAB = "PAST"

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BookingTabs(onClick: (id: Int) -> Unit) {
    // Retrieve tab data and initialize PagerState
    val tabData = getTabList()
    val pagerState = rememberPagerState(pageCount = tabData.size)
    // Column container for the entire tab layout
    Column(modifier = Modifier.fillMaxSize()) {
        // Display TabLayout
        TabLayout(tabData, pagerState)
        // Display TabContent with HorizontalPager
        TabContent(pagerState, onClick)
    }
}

// Function to get a list of tab titles
private fun getTabList(): List<String> {
    return listOf(
        CURRENT_TAB, PAST_TAB
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(
    tabData: List<String>,
    pagerState: PagerState,
) {
    // Coroutine scope for launching animations
    val scope = rememberCoroutineScope()

    // TabRow displaying tabs with indicators
    TabRow(selectedTabIndex = pagerState.currentPage, divider = {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_5_dp)))
    }, backgroundColor = AppColor, contentColor = TabsColor, indicator = { tabPositions ->
        // Indicator for the selected tab
        TabRowDefaults.Indicator(
            modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
            height = dimensionResource(id = R.dimen.dim_5_dp),
            color = SelectionColor
        )
    }, modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()

    ) {
        // Create tabs based on tabData
        tabData.forEachIndexed { index, tabTitle ->
            Tab(selected = pagerState.currentPage == index, onClick = {
                // Animate scrolling to the selected tab
                scope.launch {
                    pagerState.animateScrollToPage(index)
                }
            }, text = {
                // Display tab title as text
                Text(text = tabTitle)
            })
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabContent(
    pagerState: PagerState, onClick: (id: Int) -> Unit
) {
    // HorizontalPager for switching between tab content
    HorizontalPager(state = pagerState) { index ->
        // Display BookingList based on the current tab index
        when (index) {
            0 -> {
                BookingList(onClick,true)
            }

            1 -> {
                BookingList(onClick,false)
            }
        }
    }

}