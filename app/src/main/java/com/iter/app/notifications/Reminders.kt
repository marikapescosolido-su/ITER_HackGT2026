package com.iter.app.notifications

/**
 * TODO(team): daily check-in reminder at the patient's chosen time.
 *
 * Plan:
 *  1. Add POST_NOTIFICATIONS permission to AndroidManifest.xml and ask for it on the patient home screen.
 *  2. Create a notification channel "check-in reminders".
 *  3. Schedule with AlarmManager or WorkManager (WorkManager needs a new dependency).
 *  4. Text comes from SupportMessages.reminder(...). Never show mood details on the lock screen.
 *
 * For the demo video, a "Send test reminder" button that posts the notification right away is enough.
 */
object Reminders
