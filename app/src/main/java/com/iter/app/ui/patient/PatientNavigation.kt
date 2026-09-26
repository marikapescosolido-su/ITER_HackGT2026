package com.iter.app.ui.patient

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.iter.app.ui.navigation.Routes
import com.iter.app.ui.patient.checkin.CheckInDoneScreen
import com.iter.app.ui.patient.checkin.CheckInScreen
import com.iter.app.ui.patient.home.AccountScreen
import com.iter.app.ui.patient.home.MissedSurveyScreen
import com.iter.app.ui.patient.home.NotificationsScreen
import com.iter.app.ui.patient.home.PatientHomeScreen
import com.iter.app.ui.patient.onboarding.OnboardingScreen
import com.iter.app.ui.patient.safety.SafetyScreen
import com.iter.app.ui.patient.sharing.SharingScreen
import com.iter.app.ui.patient.weekly.Phq9Screen

fun NavGraphBuilder.patientScreens(nav: NavController) {
    composable(Routes.PATIENT_HOME) {
        PatientHomeScreen(onBack = { nav.popBackStack() }, onOpen = { nav.navigate(it) })
    }
    composable(Routes.ONBOARDING) {
        OnboardingScreen(onBack = { nav.popBackStack() }, onFinished = {
            nav.navigate(Routes.PATIENT_HOME) { popUpTo(Routes.DEMO) }
        })
    }
    composable(Routes.CHECK_IN) {
        CheckInScreen(onBack = { nav.popBackStack() }, onSubmitted = {
            nav.navigate(Routes.CHECK_IN_DONE) { popUpTo(Routes.PATIENT_HOME) }
        })
    }
    composable(Routes.CHECK_IN_DONE) { CheckInDoneScreen(onDone = { nav.popBackStack() }) }
    composable(Routes.PHQ9) {
        Phq9Screen(onBack = { nav.popBackStack() }, onSubmitted = { needsSafety ->
            if (needsSafety) {
                nav.navigate(Routes.SAFETY) { popUpTo(Routes.PATIENT_HOME) }
            } else {
                nav.popBackStack()
            }
        })
    }
    composable(Routes.SAFETY) { SafetyScreen(onDone = { nav.popBackStack() }) }
    composable(Routes.SHARING) { SharingScreen(onBack = { nav.popBackStack() }) }
    composable(Routes.NOTIFICATIONS) {
        NotificationsScreen(onBack = { nav.popBackStack() }, onOpen = { nav.navigate(it) })
    }
    composable(Routes.ACCOUNT) { AccountScreen(onBack = { nav.popBackStack() }, onOpen = { nav.navigate(it) }) }
    composable(Routes.MISSED_SURVEY) {
        MissedSurveyScreen(
            onTakeNow = { nav.navigate(Routes.CHECK_IN) { popUpTo(Routes.PATIENT_HOME) } },
            onNotToday = { nav.popBackStack(Routes.PATIENT_HOME, inclusive = false) },
        )
    }
}
