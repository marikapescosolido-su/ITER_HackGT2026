package com.iter.app.ui.clinician

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.iter.app.ui.navigation.Routes

fun NavGraphBuilder.clinicianScreens(nav: NavController) {
    composable(Routes.REPORT) {
        ReportScreen(onBack = { nav.popBackStack() }, onOpenSettings = { nav.navigate(Routes.REPORT_SETTINGS) })
    }
    composable(Routes.REPORT_SETTINGS) { ReportSettingsScreen(onBack = { nav.popBackStack() }) }
}
