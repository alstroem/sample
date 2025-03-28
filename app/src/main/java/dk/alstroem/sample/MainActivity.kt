package dk.alstroem.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dk.alstroem.core.designsystem.theme.SampleTheme
import dk.alstroem.feature.weather.events.EventDetailsScreen
import dk.alstroem.feature.weather.events.EventsScreen
import dk.alstroem.feature.weather.navigation.EventDetails
import dk.alstroem.feature.weather.navigation.EventOverview
import dk.alstroem.feature.weather.navigation.Events
import dk.alstroem.feature.weather.navigation.Sensor
import dk.alstroem.sample.navigation.topLeverRoutes
import dk.alstroem.feature.weather.overview.OverviewScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SampleTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            val navStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navStackEntry?.destination
                            topLeverRoutes.forEach { topLevelRoute ->
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            imageVector = topLevelRoute.icon,
                                            contentDescription = topLevelRoute.name
                                        )
                                    },
                                    label = { Text(text = topLevelRoute.name) },
                                    selected = currentDestination?.hierarchy?.any {
                                        it.hasRoute(
                                            topLevelRoute.route::class
                                        )
                                    } == true,
                                    onClick = {
                                        navController.navigate(topLevelRoute.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    SampleNavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun SampleNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Sensor
    ) {
        composable<Sensor> {
            OverviewScreen()
        }
        navigation<EventOverview>(startDestination = Events) {
            composable<Events> {
                EventsScreen(
                    openEventDetails = { timestamp ->
                        navController.navigate(
                            route = EventDetails(timestamp = timestamp)
                        )
                    }
                )
            }
            composable<EventDetails> {
                EventDetailsScreen()
            }
        }
    }
}
