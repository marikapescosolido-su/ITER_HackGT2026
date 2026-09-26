package com.iter.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iter.app.ui.clinician.clinicianScreens
import com.iter.app.ui.demo.DemoLauncherScreen
import com.iter.app.ui.patient.patientScreens
import com.iter.app.ui.supporter.supporterScreens

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val nav = rememberNavController()
    NavHost(nav, startDestination = Routes.DEMO, modifier = modifier) {
        composable(Routes.DEMO) { DemoLauncherScreen(onOpen = { nav.navigate(it) }) }
        patientScreens(nav)
        supporterScreens(nav)
        clinicianScreens(nav)
    }
}
