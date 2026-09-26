package com.team.checkin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.team.checkin.ui.checkin.CheckInScreen
import com.team.checkin.ui.home.HomeScreen
import com.team.checkin.ui.partner.PartnerScreen
import com.team.checkin.ui.report.ReportScreen
import com.team.checkin.ui.theme.CheckinTheme

object Routes {
    const val HOME = "home"
    const val CHECK_IN = "check_in"
    const val PARTNER = "partner"
    const val REPORT = "report"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CheckinTheme {
                val nav = rememberNavController()
                Scaffold { padding ->
                    NavHost(nav, startDestination = Routes.HOME, modifier = Modifier.padding(padding)) {
                        composable(Routes.HOME) {
                            HomeScreen(
                                onStartCheckIn = { nav.navigate(Routes.CHECK_IN) },
                                onOpenPartner = { nav.navigate(Routes.PARTNER) },
                                onOpenReport = { nav.navigate(Routes.REPORT) },
                            )
                        }
                        composable(Routes.CHECK_IN) { CheckInScreen(onDone = { nav.popBackStack() }) }
                        composable(Routes.PARTNER) { PartnerScreen(onDone = { nav.popBackStack() }) }
                        composable(Routes.REPORT) { ReportScreen(onDone = { nav.popBackStack() }) }
                    }
                }
            }
        }
    }
}
