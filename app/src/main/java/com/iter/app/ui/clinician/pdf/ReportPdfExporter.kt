package com.iter.app.ui.clinician.pdf

import android.content.Context
import com.iter.app.domain.WeeklyReport

/**
 * TODO(team): turn the WeeklyReport into a PDF and open the share sheet (email app).
 *
 * Plan (no new libraries needed):
 *  1. android.graphics.pdf.PdfDocument: A4 page is 595 x 842 points.
 *  2. Draw with android.graphics.Canvas + Paint: title, patient + period, report.refillAlerts near the top
 *     ("Refill soon: Sertraline 100 mg, about 4 days left"), the stat numbers,
 *     the summary bullets (label them "AI-assisted"), simple line charts (drawLine per point,
 *     skip nulls), side effects, supporter view, missing days, notes, then every answer.
 *  3. Save to context.cacheDir/reports/iter-report.pdf.
 *  4. Share: add a FileProvider to AndroidManifest.xml (+ res/xml/file_paths.xml with <cache-path>),
 *     then Intent.ACTION_SEND with type "application/pdf",
 *     EXTRA_EMAIL = DemoRepository.reportSettings.doctorEmail, EXTRA_STREAM = the file's content Uri.
 *
 * Return true when the share sheet opened.
 */
object ReportPdfExporter {
    fun export(context: Context, report: WeeklyReport, insights: List<String>): Boolean = false
}
