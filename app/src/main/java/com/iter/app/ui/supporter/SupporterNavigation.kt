package com.iter.app.ui.supporter

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.iter.app.ui.navigation.Routes

/** The demo supporter is Sam (the patient's partner). */
const val DEMO_SUPPORTER = "Sam"

fun NavGraphBuilder.supporterScreens(nav: NavController) {
    composable(Routes.SUPPORTER_HOME) {
        SupporterHomeScreen(onBack = { nav.popBackStack() }, onOpen = { nav.navigate(it) })
    }
    composable(Routes.NUDGE) { NudgeScreen(onBack = { nav.popBackStack() }) }
    composable(Routes.SUPPORTER_SURVEY) { SupporterSurveyScreen(onBack = { nav.popBackStack() }) }
}
