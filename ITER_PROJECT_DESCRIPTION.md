# ITER: A Precision-Medicine Monitoring and Support App

ITER is a digital health application designed to bring a **precision-medicine approach to the monitoring of depression treatments** and, in the future, other conditions that require ongoing medication management, such as epilepsy. The name “ITER,” derived from the Latin word for “journey” or “path,” reflects the application’s central purpose: helping patients, clinicians, and trusted supporters understand a person’s treatment journey over time.

Today, decisions about antidepressant medication can sometimes **feel inconsistent, subjective, or heavily dependent on trial and error**. A medication that works well for one person may be ineffective or produce difficult side effects for another. However, clinicians often have **limited information** between appointments. A patient may be seen only once a month or less, and during that appointment, they may struggle to remember precisely how they felt on individual days, when a symptom changed, how well they slept, or whether a side effect appeared after a dosage adjustment. Recent emotions can also influence how patients remember the preceding weeks.

As a result, treatment decisions may be based on incomplete or retrospective information. **This can delay the identification of an effective medication, make side effects harder to recognize, and increase the time required to determine whether a treatment is helping**.

ITER addresses this problem by collecting brief, structured, patient-reported information every day and transforming it into a clear longitudinal picture of the patient’s health. Instead of relying almost entirely on a patient’s memory during an occasional appointment, clinicians can review patterns recorded throughout the treatment period. The application does not prescribe medication, diagnose conditions, or replace clinical judgment. Its role is to improve the quality, continuity, and interpretability of the information available to patients and healthcare professionals.

## From trial-and-error care to precision medicine

The opposite of precision medicine can be described as a “one-size-fits-all” or trial-and-error approach. In such a model, treatment decisions are largely based on population averages, broad diagnostic categories, occasional observations, and generalized assumptions about how a typical patient might respond.

Precision medicine aims to make care more individualized. It considers the person’s symptoms, medication response, medical history, family history, daily experiences, physical measurements, environmental circumstances, and changes over time. **ITER supports this model by building an individualized record of the patient’s response to treatment**.

The application is initially focused on depression medication monitoring, but its structure is intended to be scalable. Different medications and conditions can require different questions, measurements, warning signs, and reporting schedules. For example, a future version designed for epilepsy could incorporate seizure occurrence, seizure type, medication adherence, sleep, possible triggers, recovery time, and observations from a caregiver. The underlying principle would remain the same: collect small amounts of relevant information consistently so that care can become more evidence-based and personalized.

## Patient onboarding and baseline assessment

When a patient first joins ITER, they complete an **initial assessment** that is longer and more comprehensive than the daily check-in. This onboarding survey creates the patient’s baseline and gives the application-and, with the patient’s permission, their healthcare team—the context needed to interpret future changes.

The assessment can include:

- Basic personal and demographic information.
- Current diagnoses and relevant previous diagnoses.
- Current depression medication, dosage, and treatment start date.
- Other prescription medications, over-the-counter products, and supplements.
- Previous psychiatric medications and the patient’s responses to them.
- Known side effects, allergies, or medication sensitivities.
- Relevant physical-health conditions.
- Family medical and mental-health history.
- Previous experiences with depression, anxiety, or other conditions.
- Typical sleep patterns.
- Current emotional and physical symptoms.
- Existing support systems.
- Lifestyle and daily-routine information relevant to treatment.
- Baseline measurements such as blood pressure, when applicable.
- Communication and reminder preferences.
- The patient’s chosen doctor, clinic, or connected hospital.
- Consent preferences for sharing information with clinicians, supporters, and researchers.

The onboarding experience should be **calm, understandable, and nonjudgmental**. Sensitive questions should be introduced with a clear explanation of why they are being asked. Patients should be able to pause and continue later, skip nonessential questions when appropriate, and review their answers before submitting them.

The baseline assessment is important because the same daily response can have different meanings for different people. A sleep duration that represents a serious change for one patient may be normal for another. Medication response can also be influenced by medical history, other medications, family history, and the patient’s typical emotional and physical state. ITER uses this baseline as context rather than treating every patient according to an identical standard.

## The daily one-minute check-in

The primary patient experience is a **short daily survey that should generally take between one and three minutes**. Its purpose is to capture the patient’s physical and emotional state while the experience is still recent.

The daily survey may ask about:

- Overall mood.
- Anxiety level.
- Energy and motivation.
- Sleep duration and sleep quality.
- Ability to concentrate.
- Physical well-being.
- Medication adherence.
- Changes in medication or dosage.
- Side effects.
- Appetite.
- Stress.
- Social connection or feelings of isolation.
- Ability to complete ordinary daily activities.
- Optional health measurements, such as blood pressure.
- An optional short note describing how the patient felt or what affected their day.

Questions should be short, neutral, supportive, and carefully reviewed to avoid unnecessarily triggering language. The survey should not feel like an examination or an obligation to demonstrate improvement. It should feel like a **private moment of reflection** that helps the patient and their clinician understand the treatment process.

The questions can adapt over time. The app may modify the daily survey according to the medication being monitored, the patient’s background, the stage of treatment, previously reported side effects, and the condition being managed. **Personalization must remain clinically governed and transparent**: the patient and clinician should be able to understand why particular questions are being asked.

ITER should also avoid asking every possible question every day. Core questions can remain consistent to preserve reliable comparisons, while secondary questions rotate or appear when they become relevant. This keeps the check-in brief without losing clinically meaningful information.

## Helping the patient feel less alone

A central design challenge is ensuring that the daily survey does not feel cold, isolating, or mechanical. ITER should communicate that the patient is recording information as part of a supported treatment journey.

The experience can include:

- A gentle greeting and a clear estimate of the time required.
- Reassuring language that does not pressure the patient to feel better.
- A visible indication that the patient’s information will contribute to their next clinical review.
- Supportive acknowledgements after completion.
- Carefully written messages based on recent patterns.
- Progress indicators showing how consistent tracking is creating a clearer picture.
- Optional involvement from a trusted supporter.
- Simple explanations of trends without making diagnoses.
- Immediate access to appropriate support information when necessary.

Supportive messages should not make unsupported claims such as “You are improving” based on a single answer. Instead, the app might say, “Thank you for checking in today,” “You have completed today’s check-in,” or “It looks like today was more difficult than yesterday. Your response has been recorded so that the change is not lost.”

ITER should **not use artificial positivity or language that could make the patient feel guilty**. The application’s tone should remain warm, calm, respectful, and **clinically responsible**.

## Notifications, reminders, and supportive nudging

ITER includes a notification system designed to encourage continuity without becoming intrusive. If a patient has not completed the daily survey, the application can send a **gentle reminder** at the time selected by the patient.

The content and timing of notifications may reflect the patient’s recent state. For example, after a more difficult day, the next reminder might use especially gentle wording. However, notifications must protect privacy: sensitive medical or emotional information **should not appear on a locked screen** unless the patient has explicitly enabled it.

The reminder system can include:

- A daily check-in reminder.
- A second reminder if the survey remains incomplete.
- Medication reminders, when enabled.
- Notifications about an upcoming clinician review.
- Confirmation that a report has been prepared or shared.
- Supportive messages based on recent check-in patterns.
- Notifications when a trusted person sends an approved nudge.
- Reminders to enter a requested health measurement.

The patient can control the frequency, timing, channel, and level of detail of these notifications.

A **completion streak** can encourage patients to establish a **daily habit**. The streak creates a small amount of positive external motivation: many users will want to avoid breaking a routine they have maintained. At the same time, the feature must not punish or shame someone for missing a day. Depression itself can make routine tasks difficult, so a missed check-in should be treated with compassion. **The application may preserve progress milestones, offer a grace period, or say, “You can start again today,” instead of presenting the missed day as a failure**.

Every **nudge sent by a trusted partner or friend** can be accompanied by a kind and encouraging sentence. Rather than functioning as a simple reminder, the nudge should feel personal and supportive. For example, a message might say, “You’ve got this—your check-in will only take a minute,” or “I’m thinking of you today. Remember to complete your check-in when you feel ready.”

When both people choose to activate the feature, nudging and survey completion can also become a friendly challenge between partners or friends. They might create a shared consistency goal, celebrate a number of completed check-ins, or encourage one another to maintain their routines. This challenge should remain collaborative rather than competitive and should never create pressure, guilt, or judgment. Its purpose is to make participation feel more engaging and to remind patients that they are not completing the process alone.

## Tracking interaction patterns

In addition to survey responses, ITER can—with appropriate consent—record interaction signals such as:

- How often the patient opens the application.
- When the patient completes the daily check-in.
- The number of reminders needed.
- Changes in completion consistency.
- Repeated visits to particular support areas.
- Unusual increases in app openings or repeated clicks.
- The time taken to complete a check-in.
- Sudden changes in ordinary usage patterns.

These signals may provide useful context. For example, **a sharp increase in repetitive app use could be associated with anxiety or uncertainty**. It must not automatically be treated as proof of a clinical problem. Instead, it can be considered alongside survey responses and other data.

When the system identifies a pattern that may indicate distress or concern, it could offer an **optional short, approximately 30-second voice check-in**. A conversational voice service, potentially powered by technology such as ElevenLabs, could guide the patient through a brief calming or clarifying interaction. The voice experience should be clearly identified as automated, should never impersonate a clinician, and should not claim to provide emergency or professional psychological care.

Any automated escalation logic must be clinically validated. If responses suggest an immediate safety concern, ITER should follow a clearly defined safety protocol, present crisis or emergency resources appropriate to the patient’s location, and encourage direct human support. The app must make its limitations explicit and must never create the impression that it is continuously monitored by emergency professionals unless that service truly exists.

## Medication-specific and personalized monitoring

ITER is designed as a scalable system rather than a fixed depression questionnaire. Each medication can have a monitoring profile containing relevant symptoms, side effects, timing considerations, and physical measurements.

For example, the application could adjust questions according to:

- The medication being taken.
- Dosage and recent dosage changes.
- The amount of time since treatment began.
- Known or clinically relevant side effects.
- Other medications the patient is taking.
- The patient’s physical-health conditions.
- Family history.
- Previous medication experiences.
- Baseline mood, sleep, and anxiety levels.
- The clinician’s monitoring priorities.

Clinicians must be able to see the patient’s complete medication list so that responses are not interpreted in isolation. The system can also help the patient keep that list accurate by asking for confirmation when medication changes occur.

Personalization should make the survey more relevant, not more alarming. Questions must remain non-leading and non-triggering. The system should avoid presenting possible side effects in a way that encourages expectation bias, while still collecting the information required for safe and effective monitoring.

ITER may organize evidence that helps clinicians evaluate a medication, but it should not independently instruct a patient to begin, discontinue, or change a prescription. Medication recommendations and adjustments remain the responsibility of qualified healthcare professionals.

## The clinician and healthcare-provider experience

Healthcare providers need a dedicated clinical interface that allows them to configure monitoring and review patient information efficiently.

A clinician should be able to:

- View patients assigned to them.
- See which medications each patient is currently taking.
- Review the patient’s baseline assessment and relevant family history.
- Set the frequency at which reports are generated.
- Select the number of monitoring days included in a report.
- Request specific survey modules or health measurements.
- Review daily survey answers.
- Review adherence and missing-data patterns.
- Compare symptoms before and after a medication or dosage change.
- Inspect physical and emotional trends together.
- See supporter observations when the patient has authorized access.
- Add clinical notes or mark information for follow-up.
- Export or receive reports.
- Identify patients whose data may require attention.

The default presentation should be coherent and easy to scan. Clinicians often have limited time, so the first view should communicate the most important trends without requiring them to read every daily response. Nevertheless, the underlying answers must remain available. The system should never conceal the original patient-reported data behind an AI-generated summary.

The application could eventually support medication suggestions or decision-support information, but this would be a separate, carefully regulated clinical feature. Any suggestion must be evidence-based, explainable, and presented as decision support—not as an automated prescription or replacement for a clinician.

## AI-supported analysis and visualization

At the end of each week, or after a specific number of days selected by the healthcare provider, ITER generates a structured report. Artificial intelligence can help organize and interpret the collected information so the clinician can quickly recognize patterns.

The report’s opening section should provide a strong visual overview, including graphics such as:

- Mood over time.
- Anxiety over time.
- Sleep duration and quality.
- Energy and motivation.
- Medication adherence.
- Reported side effects.
- Relevant physical measurements.
- Survey-completion consistency.
- Important changes after a dosage adjustment.
- Comparisons between patient reports and supporter observations.

The AI-generated analysis may identify correlations, changes, or patterns—for example, that sleep quality declined after a medication change or that anxiety scores were consistently higher on days when a dose was missed. These findings should be described as observations rather than proven causal relationships.

Every report should clearly distinguish among:

1. Information entered directly by the patient.
2. Information entered by a supporter.
3. Objective or device-generated measurements.
4. AI-generated summaries or interpretations.
5. Notes or decisions entered by a clinician.

The clinician must be able to review the exact answers behind every graph and AI interpretation. Where possible, summarized findings should link back to their supporting dates and responses. Missing data should be visibly marked and should never be silently interpreted as a positive or negative health result.

## PDF reports and delivery to doctors

Doctors can receive the report as a PDF by email after the reporting interval they select—for example, every seven days, every fourteen days, before an appointment, or after a medication adjustment.

A PDF may contain:

- Patient and treatment-period information.
- Current medication and dosage.
- Recent medication changes.
- A concise AI-assisted summary.
- Visual graphs showing trends.
- Notable changes and potential areas for clinical follow-up.
- Medication-adherence information.
- Side-effect patterns.
- Sleep, anxiety, mood, and physical-health trends.
- Survey-completion information.
- Trusted-supporter observations, when authorized.
- The patient’s exact survey answers.
- Optional patient notes.
- Relevant baseline and family-history context.
- Clear labels for missing information.
- A statement explaining that AI-generated content is decision support and not a diagnosis.

Because email can introduce privacy risks, reports should be delivered through a secure clinical channel whenever possible. If email is used, the system should use appropriate encryption, access controls, protected links, expiration rules, or password-protected documents in accordance with applicable healthcare and privacy requirements.

The report schedule is controlled by the healthcare provider, but the patient should be able to see when information will be shared and with whom. Depending on clinical policy, the patient may also be able to preview or download their report.

## Shared support mode

ITER includes a consent-based shared mode that allows a patient to involve a trusted family member, partner, friend, or caregiver. This feature acknowledges that someone close to the patient may notice changes that the patient does not recognize or may be able to provide encouragement when completing a daily check-in feels difficult.

The patient remains in control of access. They choose whom to invite, what information each person can see, what actions they can perform, and whether access is temporary or ongoing.

Three principal support roles are available:

### Viewer

A viewer can see only the information the patient has chosen to share. This might include whether the daily check-in was completed, a limited status indicator, selected trends, or a weekly summary. A viewer cannot modify patient data.

If the patient activates the appropriate permission, a viewer can also receive notifications about how the patient has been feeling. These notifications should be brief, compassionate, and intentionally limited so that they support human connection without disclosing unnecessary medical details. For example, the viewer might receive a message such as: “Hi, yesterday Alex felt a bit down. Remember to be kind and check in when you can.”

The wording should avoid making a diagnosis, exaggerating a single response, or telling the viewer exactly how to act. Similar notifications might say, “Sam had a more difficult day yesterday. A small message of support may be appreciated,” or “Jordan reported feeling lower than usual. Consider checking in today.”

This feature must be optional and fully controlled by the patient. The patient should be able to choose which types of updates can be shared, how frequently notifications are sent, and whether they include the patient’s name or more general wording. They should also be able to pause the notifications or disable them at any time.

### Nudger

A nudger can send a supportive reminder if the patient has not completed a check-in. Nudges should be limited in frequency and use respectful templates so they cannot become intrusive or coercive. The patient should be able to mute a person’s nudges or revoke access at any time.

Each nudge can include a warm, encouraging sentence written or selected by the trusted person. This makes the interaction feel like genuine support rather than an automated warning. The partner or friend might send a message such as, “Just a little reminder—you’re doing great, and I’m here for you,” or “Take your time, but remember that today’s one-minute check-in can help your care team understand how you’re feeling.”

When enabled by both participants, the nudging experience can also include a friendly shared challenge. Partners or friends can work toward an agreed consistency goal, celebrate milestones together, or motivate one another to complete their check-ins. The challenge should focus on mutual encouragement and participation, never on comparing mental-health outcomes or deciding who is “doing better.”

### Editor

An editor has more extensive permissions but should not silently alter the patient’s original account. They may propose a correction, help enter approved factual information, or submit an observation. Any edit should be visibly attributed to that person, timestamped, and either approved by the patient or preserved as a separate supporter entry. Original patient responses should remain available in the audit history.

The trusted person can have a linked version of their account and receive notifications based on the permissions granted by the patient. For example, they might be notified that a check-in has not been completed, receive an optional update about how the patient has been feeling, or be invited to submit a weekly observation. They should not receive sensitive details unless the patient has explicitly shared them.

## The supporter survey

In addition to viewing or nudging, a trusted person may complete a brief survey from their own perspective. This survey could be completed weekly rather than daily to reduce burden.

It may ask whether the supporter observed changes in:

- Mood or emotional expression.
- Energy.
- Sleep patterns.
- Social engagement.
- Ability to complete ordinary activities.
- Physical well-being.
- Medication routine.
- Behavior that appears unusual for the patient.
- Overall improvement or decline.

Supporter observations must never replace the patient’s own voice. They should be displayed as a separate perspective, not as objective truth. Differences between the two accounts may be clinically meaningful, but the application should present them neutrally. The patient must know that the survey exists and control whether it is included in clinical reports.

## Connection to hospitals and research

ITER is intended to connect with the hospital, clinic, or health system in which the patient’s doctor works. This connection allows authorized healthcare providers to receive reports, review trends, and potentially incorporate relevant information into the clinical workflow.

The patient may also choose whether their information can be collected by the connected institution for research. Research participation must be optional and separate from consent for ordinary treatment monitoring. Refusing research participation should not prevent the patient from using the core application or receiving care.

The consent process should clearly explain:

- What information will be collected.
- Whether information will be identifiable, coded, or de-identified.
- Which institution will receive it.
- What kinds of research it may support.
- How long it may be stored.
- Whether it may be shared with approved research partners.
- How the patient can withdraw consent.
- What happens to information already included in completed research.
- Who to contact with questions.

Where appropriate, research data should be de-identified or pseudonymized and separated from information used for direct care. Access should be controlled, logged, and limited to authorized teams operating under the necessary ethical and regulatory approvals.

## Privacy, consent, and patient control

ITER handles highly sensitive mental-health and medical information. Privacy and consent are therefore core product functions, not secondary settings.

Patients should be able to:

- See exactly who has access to their information.
- Control which categories of information are shared.
- Grant different permissions to clinicians and supporters.
- Decide whether viewers can receive notifications about their emotional state.
- Choose the level of detail included in supporter notifications.
- Grant, pause, or withdraw access to friendly partner challenges.
- Withdraw a supporter’s access.
- Change research-consent preferences.
- Review a history of access and changes.
- Correct inaccurate personal or medication information.
- Export their information.
- Request deletion when legally and clinically permitted.
- Understand how AI is used.
- Know when a report has been generated, viewed, or sent.

Data should be encrypted during transmission and storage. Access should use secure authentication and role-based permissions. Sensitive actions should be recorded in an audit log. Clinical, supporter, administrative, and research access should remain appropriately separated. The system should be designed to comply with the healthcare, privacy, security, accessibility, and medical-device requirements applicable in each region where it operates.

## Safety and clinical responsibility

ITER supports treatment monitoring but does not replace medical care. It should not diagnose depression, determine independently that medication is effective, or tell a patient to change or stop treatment.

The application must clearly communicate that:

- AI interpretations may be incomplete or incorrect.
- Correlation does not establish that medication caused a change.
- Clinicians must review original responses.
- Automated messages are not equivalent to human monitoring.
- The app is not an emergency service unless a genuine staffed emergency pathway is explicitly provided.
- Patients should contact an appropriate healthcare professional for treatment decisions.
- Urgent or emergency concerns require immediate access to local emergency or crisis resources.

Clinical experts should review all surveys, adaptive-question rules, report logic, notification language, and safety pathways. The system should also be tested for demographic and clinical bias. Because the app is intended to reduce bias in treatment decisions, it must not introduce new bias through its questionnaires, AI models, data collection, or visualizations.

## The complete ITER experience

The ITER journey begins with a comprehensive onboarding assessment that establishes the patient’s medical, emotional, medication, and family-history context. The patient then completes a short daily check-in that records mood, anxiety, sleep, physical well-being, medication adherence, side effects, and optional health measurements.

The app gently reminds the patient when a check-in is due, recognizes consistent participation through a compassionate streak system, and provides supportive language informed by recent responses. With consent, interaction patterns can supply additional context and may lead to an optional brief voice-based check-in when the system identifies possible concern.

A trusted family member, friend, partner, or caregiver can participate as a viewer, nudger, or editor, according to the permissions selected by the patient. Viewers can receive optional, compassionate notifications about how the patient has been feeling, while nudges can be accompanied by encouraging sentences. When both people choose to participate, daily check-ins can also become a friendly shared challenge that helps partners or friends motivate and support one another. The trusted person can additionally complete a periodic observer survey, creating a second perspective that remains clearly separated from the patient’s own account.

At a schedule selected by the healthcare provider, ITER converts the accumulated information into a clear report. The report begins with accessible graphics and an AI-assisted interpretation, followed by the complete underlying responses so the doctor can verify every conclusion. The report is delivered securely as a PDF or made available through the connected clinical system.

The doctor can then use a continuous record rather than relying only on a brief conversation and the patient’s memory of the previous month. The patient gains a clearer voice in the treatment process, the clinician receives more coherent evidence, trusted supporters can help without taking control away from the patient, and—when the patient explicitly agrees—the connected hospital can use appropriately protected information to support research.

ITER’s ultimate purpose is not simply to collect more data. It is to collect the right information, at the right frequency, with the patient’s informed consent, and turn it into a clear and humane account of how treatment is affecting an individual person. In this way, ITER can help move medication monitoring away from fragmented, one-size-fits-all trial and error and toward more continuous, collaborative, and precise care.
