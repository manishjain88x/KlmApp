package com.klmapp.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.klmapp.NavigationItem
import com.klmapp.ui.theme.AppColor
import com.klmapp.ui.theme.SelectedNavColor
import com.klmapp.utils.Constants.KEY_ID

/**
 * Constant representing the route for booking details with a dynamic ID parameter.
 */
private const val BOOKING_DETAILS_ROUTE = "detail/{id}"

/**
 * Constant representing the common part of the route for booking details.
 */
private const val DETAILS = "detail/"

/**
 * Composable function for the main screen of the application.
 */
@Composable
fun MainScreen() {
    //NavController to handle navigation within the app
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            // Display the bottom navigation bar if the current screen is not the details screen
            if (isDetailsScreen(navController).not()) {
                BottomNavigationBar(navController)
            }
        },
        content = { padding ->
            // Box is a layout composable that stacks its children on top of each other
            Box(modifier = Modifier.padding(padding)) {
                // Display the main content with navigation
                Navigation(navController = navController)
            }
        },
    )
}

/**
 * Composable function for the bottom navigation bar.
 */
@Composable
fun BottomNavigationBar(navController: NavController) {
    // List of navigation items for the bottom navigation bar
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Booking,
    )
    // BottomNavigation is a composable that provides a bottom navigation bar
    BottomNavigation(
        backgroundColor = AppColor, contentColor = Color.Black
    ) {
        // Get the current route from the navigation controller
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        // Create BottomNavigationItem for each navigation item
        items.forEach { item ->
            BottomNavigationItem(icon = {
                Icon(
                    painterResource(id = item.icon), contentDescription = item.title
                )
            },
                label = { Text(text = item.title) },
                selectedContentColor = SelectedNavColor,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                })
        }
    }
}

/**
 * Composable function for handling navigation in the app.
 */
@Composable
fun Navigation(navController: NavHostController) {
    // NavHost is a composable that hosts the navigation graph
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        // Define composable destinations for each navigation item
        composable(NavigationItem.Home.route) {
            HomeScreen()
        }
        composable(NavigationItem.Booking.route) {
            // Navigate to the BookingScreen and provide a callback for handling item clicks
            BookingScreen {
                navController.navigate("$DETAILS${it}")
                Log.d("Id:", it.toString())
            }
        }

        composable(route = BOOKING_DETAILS_ROUTE, arguments = listOf(navArgument(KEY_ID) {
            type = NavType.IntType
        })) {
            BookingDetailsScreen(navController)
        }
    }
}

/**
 * Function to check if the current screen is the booking details screen.
 */
@Composable
fun isDetailsScreen(navController: NavController): Boolean {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    return currentRoute == BOOKING_DETAILS_ROUTE
}

@Preview(showBackground = true, widthDp = 360, heightDp = 400)
@Composable
fun MainScreenPreview() {
    MainScreen()
}