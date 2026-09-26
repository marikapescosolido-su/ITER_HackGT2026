# ITER: code map and team split

Android demo, Kotlin + Jetpack Compose. No backend: `DemoRepository` holds everything in memory
and resets on restart. All code is under `app/src/main/java/com/iter/app/`.

## How the code is organized

```
data/
  model/            Plain data classes (one topic per file)
    Patient.kt        patient, medications, dose changes, baseline
    Question.kt       the 0-10 daily questions and their "why we ask" text
    CheckIn.kt        one daily check-in
    Phq9.kt           weekly PHQ-9 + safety flag
    SideEffect.kt
    Support.kt        supporter roles, nudges, supporter observations
    Settings.kt       sharing/consent settings, clinician report settings
    DataSource.kt     patient / supporter / AI / clinician labels
  sample/           The fake 6-week patient "Alex" used in the demo
  DemoRepository.kt In-memory state every screen reads and writes

domain/             Logic only, no UI. Easy to change and to test.
  MedicationProfiles.kt  side effects per medication
  Streak.kt              streak with a one-day grace period
  Lantern.kt             weekly lantern level (+1 per check-in, -1 per missed day, never out)
  SupporterSummary.kt    the only thing a partner learns about mood: lower / same / brighter
  ReportSchedule.kt      report every 3 days, 1 week or 2 weeks
  SupportMessages.kt     ALL supportive wording in one place
  Safety.kt              crisis resources + disclaimer
  WeeklyReport.kt        builds the report numbers from raw data
  ReportInsights.kt      the "AI-assisted" summary (rule-based for now)

ui/
  components/       Shared building blocks: ScreenColumn, SectionCard/Panel, ScaleQuestion,
                    StatTile, SourceTag, LineChart
    buttons/          The button system (spec A-K): PrimaryButton, SecondaryButton, TertiaryButton,
                      CautionButton, IconCircleButton, UtilityButton, SegmentedControl,
                      SupportPulseButton, HelplineButton, HelplineLink, StreakChip, ChoiceChip
    icons/            IterIcons: outline icons, 1.8 stroke
    illustration/     LanternArt (logo) and BearPath (bear carrying the lantern along the week)
  navigation/       Routes.kt (all route names) + AppNavHost.kt
  demo/             Start screen (pick whose phone to show) + Design system gallery
  patient/          PatientNavigation.kt + onboarding/, home/ (dashboard, notifications,
                    missed survey, account), checkin/, weekly/ (PHQ-9), concern/ (alert
                    overlay, 30-sec guide), safety/, sharing/ (share settings, invite)
  supporter/        SupporterNavigation.kt + home, nudge, weekly survey
  clinician/        ClinicianNavigation.kt + ReportScreen, ReportSections, ReportCharts,
                    ReportSettingsScreen, MedicationContextScreen, pdf/ReportPdfExporter
  theme/            Design tokens: Brand colors (fixed), Chrome colors (light/dark),
                    Fraunces + Sora fonts, button type scale

notifications/      TODO: daily reminders
tracking/           Interaction log (only when the patient allows it)
voice/              TODO (stretch): voice check-in
```

**Adding a screen:** add a route in `ui/navigation/Routes.kt`, then register it in your area's
`*Navigation.kt`. You don't need to touch `AppNavHost.kt`.

**Changing wording:** supportive text lives in `domain/SupportMessages.kt`, so it can be reviewed in one place.

## Privacy rule (most important)

Only the clinician sees answers and scores (in the PDF). Never show the patient or the partner
numbers, charts or comparisons of how the patient felt. See docs/NEXT_STEPS.md.

## Design system rules (from the button spec)

- Never use Material `Button`, `OutlinedButton`, `TextButton`, `FilterChip` or `Card`. Use `ui/components/buttons` and `SectionCard`.
- One `PrimaryButton` per screen. A `SecondaryButton` only sits next to a primary, never alone.
- Terracotta (`HelplineButton`, `HelplineLink`) is only for crisis resources.
- `SegmentedControl` is only for assigning a supporter role. `SupportPulseButton` is only for the Concern Alert overlay.
- Fraunces for page titles only; Sora for everything else (Material typography is already set up this way).
- Colors: use `Brand.*` for brand colors and `IterTheme.chrome.*` for page, panel, text and hairline, so dark mode works.
- Open the **Design system** card on the start screen to see every button.
- One deliberate deviation: in dark mode, text-style brand buttons use Mist instead of Sage-700, because Sage-700 is unreadable on the dark page.

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
