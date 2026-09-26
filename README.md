# ITER

**ITER is a precision-medicine platform that turns one-minute daily check-ins into clear, AI-assisted health insights—helping patients, doctors, and trusted supporters understand medication response together.**

ITER helps patients record their mood, anxiety, sleep, physical well-being, medication adherence, side effects, and relevant health measurements between medical appointments. It converts this information into accessible trends and clinician-facing reports while supporting consent-based participation from trusted friends, partners, and caregivers.

## Full project description

For the complete concept—including patient onboarding, daily surveys, personalized medication monitoring, supportive nudging, shared challenges, viewer notifications, clinician reports, AI-assisted analysis, hospital research consent, privacy, and safety—read the [full ITER project description](ITER_PROJECT_DESCRIPTION.md).

> ITER is intended to support clinical monitoring and informed conversations. It does not diagnose conditions, prescribe medication, or replace qualified medical care.

## Running the demo

This repository contains an Android demo of ITER (Kotlin + Jetpack Compose). It has no backend: all data is sample data held in memory and resets when the app restarts.

1. Install [Android Studio](https://developer.android.com/studio) and finish its setup wizard.
2. Open this folder in Android Studio, wait for Gradle sync, pick an emulator or phone, and press Run.

Or from a terminal, with a phone or emulator connected:

    ./gradlew installDebug

See [CONTRIBUTING.md](CONTRIBUTING.md) for the code map and who owns which part.
