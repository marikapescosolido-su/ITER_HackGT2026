package com.iter.app.data.model

/**
 * A 0-10 slider question in the daily check-in (Daily_Check_In_Questions.md), asked in this order,
 * one per screen. Higher always means a more noticeable symptom, difficulty or concern.
 * [why] is the "Why are we asking you this?" text behind the question's info icon.
 */
enum class Question(
    val label: String,
    val prompt: String,
    val lowLabel: String,
    val highLabel: String,
    val why: String,
) {
    Mood(
        "Low mood",
        "Thinking about today, how heavy or low did your mood feel?",
        "Not at all", "Extremely",
        "Mood naturally changes, but the intensity and duration of a low mood can be clinically important. " +
            "Following it over time helps the care team see whether a change is temporary or part of a longer pattern, " +
            "including around treatment changes.",
    ),
    Enjoyment(
        "Less enjoyment",
        "Did the things you normally enjoy feel flat or less enjoyable today?",
        "They felt as enjoyable as usual", "Nothing felt enjoyable",
        "Enjoyment can change separately from sadness. A sustained loss of interest or pleasure—sometimes called " +
            "anhedonia—can be an important sign of how emotional health and treatment response are changing.",
    ),
    Sleep(
        "Sleep disruption",
        "How much did last night's sleep get in your way today—because it was too little, too much, or interrupted?",
        "Not at all", "It affected me throughout the day",
        "Both too little and too much sleep can affect mood, memory, energy, and appetite. " +
            "Sleep changes may also appear after starting a medicine or changing a dose.",
    ),
    Appetite(
        "Appetite change",
        "Did eating feel different today—less appealing than usual, or harder to stop once you started?",
        "My appetite felt normal", "It felt completely different",
        "Appetite can change with mood, sleep, stress, physical health, and medication. Tracking both the direction " +
            "and intensity helps the care team understand possible effects on nutrition, weight, energy, and treatment tolerance.",
    ),
    Tension(
        "Tension",
        "How much tension did you carry today—like tight shoulders, a clenched jaw, a knot in your stomach, or feeling unable to settle?",
        "None", "Extreme tension",
        "Tension is one way the nervous system responds to worry or stress. New or increasing tension can also occur " +
            "with sleep disruption, medication effects, or physical restlessness, so the pattern provides useful clinical context.",
    ),
    Reading(
        "Effort to read or watch",
        "If you tried to read a book, watch a film, or follow a show today, how much effort did it take?",
        "It felt easy", "I could not manage it",
        "Following a story uses attention, memory, processing speed, and mental energy. When a familiar activity suddenly " +
            "requires much more effort, it can reveal cognitive fatigue that a general mood question might miss.",
    ),
    Messages(
        "Avoiding calls and messages",
        "How much did you find yourself avoiding phone calls or putting off replies, even from people you usually feel comfortable with?",
        "Not at all", "I avoided nearly all of them",
        "Responding to people requires social energy, attention, and motivation. A change in communication habits can " +
            "provide an early sign of withdrawal, mental overload, or difficulty managing everyday demands.",
    ),
    Digestion(
        "Digestion",
        "How much did digestive problems bother you today—such as nausea, stomach discomfort, constipation, diarrhea, or indigestion?",
        "Not at all", "Extremely",
        "Digestive symptoms can be connected to food, stress, illness, or medication. Their timing is especially useful " +
            "when assessing medication tolerance, hydration, nutrition, and whether a symptom appeared after a treatment change.",
    ),
    Irritability(
        "Irritability",
        "How easily did small things get under your skin today?",
        "No more than usual", "Almost everything irritated me",
        "Irritability can change even when sadness does not. It may be connected to mood, anxiety, poor sleep, pain, " +
            "increased activation, or medication effects, and it can influence relationships and daily functioning.",
    ),
    LosingTrack(
        "Losing track of tasks",
        "How often did you start doing something and then forget what you were doing or leave it unfinished without meaning to?",
        "Never", "With almost every task",
        "Keeping track of an activity uses working memory and executive function. Difficulties here can affect cooking, " +
            "driving, work, appointments, and taking medication safely.",
    ),
    Exhaustion(
        "Physical exhaustion",
        "How physically drained did your body feel today—even after sitting down or resting?",
        "Not drained", "Completely exhausted",
        "Physical exhaustion can be influenced by sleep, emotional strain, medication, illness, pain, or reduced activity. " +
            "Following it alongside other symptoms helps identify patterns and changes that may need a physical-health review.",
    ),
    Conversations(
        "Following conversations",
        "When someone was talking to you today, how often did their words feel hard to follow, as though your mind was foggy or a step behind?",
        "Never", "During almost every conversation",
        "Following a conversation depends on attention, memory, and processing speed. Mental fog can affect communication " +
            "and may change with sleep, stress, medication, or overall emotional and physical health.",
    ),
    Concentration(
        "Concentration",
        "When you needed to focus today, how hard was it to keep your attention on one thing without your mind drifting?",
        "It was easy", "I could not focus",
        "Concentration affects work, study, reading, driving, and decision-making. Tracking it separately from memory " +
            "helps clarify which part of thinking feels difficult and whether it is changing over time.",
    ),
    SeeingFriends(
        "Seeing friends",
        "If a friend had invited you out today, how hard would it have felt to go—even if part of you wanted to?",
        "Not hard at all", "It would have felt impossible",
        "Spending time with others depends on energy, motivation, confidence, and enjoyment. A growing gap between " +
            "wanting connection and being able to act on it can be an important change to notice.",
    ),
    EverydayBasics(
        "Everyday basics",
        "How much effort did basic things take today—like showering, getting dressed, preparing food, or tidying up?",
        "They felt manageable", "I could not manage them",
        "Everyday functioning helps show how strongly symptoms are affecting a person's life. It can also reveal " +
            "improvement that may not yet appear in an overall mood score.",
    ),
    RevvedUp(
        "Revved up",
        "Did you feel unusually energized or “revved up” today—like needing much less sleep, having racing thoughts, " +
            "talking much more, or acting more impulsively than usual?",
        "Not at all", "Extremely",
        "A sudden increase in energy or activity can be important, particularly when it appears with reduced need for " +
            "sleep, racing thoughts, or impulsive behavior. These changes may require prompt review after starting or adjusting medication.",
    ),
    SelfHarm(
        "Self-harm thoughts",
        "Since your last check-in, how strong were any thoughts about self-harm?",
        "I did not have these thoughts", "I may be in immediate danger",
        "Thoughts about death or suicide can change quickly and may not be visible to other people. Asking directly helps " +
            "the app offer immediate support and helps determine when contact with a trained professional may be needed.",
    ),
    JawNeckTension(
        "Jaw and neck tension",
        "Today, how much tension did you notice in your jaw or neck—like clenching your teeth, holding your shoulders tight, " +
            "or finding it hard to relax those muscles?",
        "Not at all", "Extremely",
        "Jaw and neck tension can be connected to stress, worry, sleep, posture, teeth grinding, pain, or medication effects. " +
            "Tracking where the tension occurs and how long it lasts can help the care team understand whether it follows " +
            "changes in mood, daily circumstances, or treatment.",
    ),
    ChillsHotFlashes(
        "Chills or hot flashes",
        "Today, did you have any sudden chills or hot flashes around the times you felt low, stressed, or overwhelmed?",
        "Not at all", "Extremely",
        "Sudden temperature sensations can accompany changes in the body’s stress response, but they may also be related " +
            "to medication, hormones, illness, or the environment. Recording when they happen and what else is happening " +
            "at the same time gives the care team useful context without assuming a single cause.",
    ),
}

/** Follow-up to [Question.Appetite] when it is above 0. */
enum class AppetiteDirection(val label: String) {
    Less("Eating less"),
    More("Eating more"),
    Changed("It changed during the day"),
}
