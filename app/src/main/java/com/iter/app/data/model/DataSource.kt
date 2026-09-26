package com.iter.app.data.model

/** Every report item says where it came from (README: "clearly distinguish among" sources). */
enum class DataSource(val label: String) {
    Patient("Reported by patient"),
    Supporter("Supporter observation"),
    Device("Measurement"),
    Ai("AI-assisted summary"),
    Clinician("Clinician note"),
}
