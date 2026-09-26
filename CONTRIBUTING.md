# ITER: code map and team split

Android demo, Kotlin + Jetpack Compose. No backend: `DemoRepository` holds everything in memory
and resets on restart. All code is under `app/src/main/java/com/iter/app/`.

## How the code is organized

```
data/
  model/            Plain data classes (one topic per file)
    Patient.kt        patient, medications, dose changes, baseline
    Question.kt       the 0-10 daily questions (core + rotating)
    CheckIn.kt        one daily check-in
    Phq9.kt           weekly PHQ-9 + safety flag
    SideEffect.kt
    Support.kt        supporter roles, nudges, supporter observations
    Settings.kt       sharing/consent settings, clinician report settings
    DataSource.kt     patient / supporter / AI / clinician labels
  sample/           The fake 6-week patient "Alex" used in the demo
  DemoRepository.kt In-memory state every screen reads and writes

domain/             Logic only, no UI. Easy to change and to test.
  DailyQuestionPlan.kt   which questions to ask today
  MedicationProfiles.kt  side effects/questions per medication
  Streak.kt              streak with a one-day grace period
  SupportMessages.kt     ALL supportive wording in one place
  Safety.kt              crisis resources + disclaimer
  WeeklyReport.kt        builds the report numbers from raw data
  ReportInsights.kt      the "AI-assisted" summary (rule-based for now)

ui/
  components/       Shared building blocks: ScreenColumn, SectionCard, ScaleQuestion,
                    StatTile, SourceTag, LineChart
  navigation/       Routes.kt (all route names) + AppNavHost.kt
  demo/             Start screen: pick whose phone to show
  patient/          PatientNavigation.kt + home/, checkin/, weekly/ (PHQ-9),
                    safety/, sharing/ (privacy), onboarding/
  supporter/        SupporterNavigation.kt + home, nudge, weekly survey
  clinician/        ClinicianNavigation.kt + ReportScreen, ReportSections,
                    ReportCharts, ReportSettingsScreen, pdf/ReportPdfExporter
  theme/            Blue + sage colors

notifications/      TODO: daily reminders
tracking/           Interaction log (only when the patient allows it)
voice/              TODO (stretch): voice check-in
```

**Adding a screen:** add a route in `ui/navigation/Routes.kt`, then register it in your area's
`*Navigation.kt`. You don't need to touch `AppNavHost.kt`.

**Changing wording:** supportive text lives in `domain/SupportMessages.kt`, so it can be reviewed in one place.

## Who owns what

| Person | Owns | Next tasks |
|---|---|---|
| A: Patient | `ui/patient/` | Polish check-in and PHQ-9, onboarding extra steps, "You're not alone" touches |
| B: Supporter + app glue | `ui/supporter/`, `ui/demo/`, `ui/navigation/`, `ui/theme/`, `data/` | Editor "suggest a correction" flow, challenge UI, app icon. Only B edits `data/model/` |
| C: Clinician report + PDF | `ui/clinician/` | Build `pdf/ReportPdfExporter.kt` (plan is in the file), share by email |
| D: Logic + extras | `domain/`, `notifications/`, `tracking/` | Reminder notification, better insights, interaction patterns |

## Rules

1. Work on your own branch; edit only files you own.
2. Need a new field in `data/model/`? Ask B.
3. Commit small, merge into `main` often.
4. Run `./gradlew assembleDebug` before merging. It must build.
