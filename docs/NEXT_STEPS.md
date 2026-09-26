# ITER: decisions and next steps

Team notes, kept next to the code so everyone sees the same list.

## Decided

- **Only the clinician sees the data.** The patient never sees scores, charts or comparisons
  (seeing "anxiety 3.7" can make things worse). The partner sees only a general level
  ("feeling lower than usual this week"), never answers or numbers.
- **The report goes out automatically.** No "send PDF" button. The clinician and patient choose
  the schedule together (every 3 days, 1 week or 2 weeks) and the PDF is emailed each time.
- **No clinician screen in the demo.** The clinician only receives the PDF.
- **Daily check-in only.** The weekly questionnaire (PHQ-9) is removed from the demo because the
  daily data already covers it. It can come back later (code is kept in `ui/patient/weekly/`).
- **Sliders with numbers, not emojis.** Numbers are more professional and easier to analyse;
  emoji faces push everyone toward the happy face.
- **Mascot: a bear carrying a lantern.** The lantern is hope. Each check-in makes it one step
  brighter through the week, and it shines brightest at the end of a full week. A missed day dims it
  one step; it never goes out. The logo is the lantern.

## To do

### Authentication (next)
- Sign up / log in for patients, supporters and clinicians, so the app knows who is using it.
- For the demo we skip it: real accounts mean creating fake users and switching between them.
- Options: Firebase Authentication (email + Google sign-in) fits Android well.

### Cloud backend (needed for automatic reports)
- Store check-ins in the cloud (for example Firebase Firestore) instead of in memory.
- A scheduled server job builds the PDF on the chosen schedule and emails it to the clinician
  (Gmail API or an email service such as SendGrid). The phone alone can't send reliably on a schedule.
- Health data needs encryption, access control and an audit log (see ITER_PROJECT_DESCRIPTION.md).

### PDF report
- `ui/clinician/pdf/ReportPdfExporter.kt` has the plan. `domain/WeeklyReport.kt` and
  `domain/ReportInsights.kt` already compute everything the PDF needs.

### Questions
- A teammate is writing the final daily questions. They live in `data/model/Question.kt`
  (one line per question) and `domain/MedicationProfiles.kt` (side effects per medication).

### Bear and lantern ideas
- A sleeping bear for the sleep question; more cubs sleeping for more hours of sleep.
- A mood picture with the bear for overall mood (only if it stays professional).
- A nicer drawn logo and bear from a designer; the current ones are drawn in code
  (`ui/components/illustration/`).

### Maybe later
- Weekly partner survey vs daily data: decide if it's redundant.
- Reminder notifications, voice check-in (ElevenLabs), interaction patterns.
